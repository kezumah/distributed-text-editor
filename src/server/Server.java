package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import compute.ServerInterface;

public class Server extends Thread implements ServerInterface {
	private int[] otherServers = new int[4];
	private  int serverPort;
	private  Map<UUID, Result> pendingChanges = Collections.synchronizedMap(new HashMap<UUID, Result>());
	private  Map<UUID,Map<Integer,Acknowledeged>> pendingPrepareAcks = Collections.synchronizedMap(new HashMap<UUID,Map<Integer,Acknowledeged>>());
	private  Map<UUID,Map<Integer,Acknowledeged>> pendingGoAcks = Collections.synchronizedMap(new HashMap<UUID,Map<Integer,Acknowledeged>>());
	RWLock lock = new RWLock();

	public String KeyValue(String functionality,String key,String value) {    
		String message="";	
		String fileName = "keyValueStore_" + serverPort + ".txt";
		KeyListStore k1 = new KeyListStore(fileName);
		try {	
			if (functionality.equalsIgnoreCase("GET")) {
				System.out.println(getCurrentTimeStamp() + ": Looking for key: "+ key+ " from client: ");
				lock.lockRead();
				message += key + " : "+ k1.isInStore(key);
				lock.unlockRead();
			} else if (functionality.equalsIgnoreCase("PUT")){
				System.out.println(getCurrentTimeStamp() + " : Writing the key: "+ key+ " and value: "+ value+ " - from client: ");
				lock.lockWrite();
				message += key + " : "+ k1.putInStore(key, value);
				lock.unlockWrite();
			} else {
				System.out.println(getCurrentTimeStamp() + " : Deleting "+key);
				lock.lockWrite();
				message += key + " : "+ k1.deleteKeyValue(key);
				lock.unlockWrite();
			}																		
		}			
		catch (Exception e) 
		{
			System.out.println(getCurrentTimeStamp() + e.getMessage());
		}
		return(message + "\n");
	}

	public String KeyValue(UUID messageId, String functionality, String key,String value) throws RemoteException {
		if (functionality.equalsIgnoreCase("GET")){
			return KeyValue(functionality, key, value);
		} else if(functionality.equalsIgnoreCase("PUT") || functionality.equalsIgnoreCase("DEL")) {
			pendingStorageAdd(messageId,  functionality,  key, value);
			tellToPrepare(messageId, functionality, key, value);
			if (!waitAckPrepare(messageId, functionality, key, value)){
				return "fail";
			}

			tellToGo(messageId);
			if (!waitToAckGo(messageId)){
				return "fail";
			}

			Result res = this.pendingChanges.get(messageId);

			if (res == null)
			{
				throw new IllegalArgumentException("The message is not in the storage");
			}

			String message = this.KeyValue(res.function, res.key, res.value);
			this.pendingChanges.remove(messageId);
			return message;
		}
		return "Incorrect command, try again";
	}

	private boolean waitToAckGo(UUID messageId) {

		int areAllAck = 0;
		int retry = 3;

		while (retry != 0){
			try{
				Thread.sleep(100);
			}catch(Exception ex){
				System.out.println(getCurrentTimeStamp() + " : wait failed");
			}

			areAllAck = 0;
			retry--;
			Map<Integer,Acknowledeged> map = this.pendingGoAcks.get(messageId);

			for (int server : this.otherServers){
				if (map.get(server).isAcked){
					areAllAck++;
				}
				else{
					callGo(messageId, server);
				}
			}
			if (areAllAck == 4){
				return true;
			}
		}

		return false;
	}

	private boolean waitAckPrepare(UUID messageId, String functionality, String key, String value) {

		int ackCount = 0, retryCount = 3;

		while (retryCount != 0){
			try{
				Thread.sleep(100);
			}catch(Exception ex){
				System.out.println(getCurrentTimeStamp() + " : wait failed");
			}
			ackCount = 0;
			retryCount--;
			Map<Integer,Acknowledeged> map = this.pendingPrepareAcks.get(messageId);
			for (int server : this.otherServers){
				if (map.get(server).isAcked){
					ackCount++;
				}else{
					callPrepare(messageId, functionality, key, value, server);
				}
			}

			if (ackCount == 4){
				return true;
			}
		}

		return false;
	}

	private void tellToPrepare(UUID messageId, String functionality, String key, String value) {
		this.pendingPrepareAcks.put(messageId, Collections.synchronizedMap(new HashMap<Integer,Acknowledeged>()));
		for (int server : this.otherServers){
			callPrepare(messageId, functionality, key, value, server);
		}

	}

	private void tellToGo(UUID mesUuid){
		this.pendingGoAcks.put(mesUuid, Collections.synchronizedMap(new HashMap<Integer, Acknowledeged>()));
		for (int server : this.otherServers){
			callGo(mesUuid, server);
		}
	}

	private void callGo(UUID messageId, int server){
		try{
			Acknowledeged a = new Acknowledeged();
			a.isAcked = false;
			this.pendingGoAcks.get(messageId).put(server, a);
			Registry registry = LocateRegistry.getRegistry(server);
			ServerInterface stub = (ServerInterface) registry.lookup("compute.ServerInterface");
			stub.go(messageId, serverPort);
		}catch(Exception ex){
			System.out.println(getCurrentTimeStamp() + " : Something went wrong in sending go, removing data from temporary storage");
		}

		System.out.println(getCurrentTimeStamp() + " : call go for worked. target: " + server);
	}

	private void callPrepare(UUID messageId, String functionality, String key,String value, int server){
		try{
			Acknowledeged a = new Acknowledeged();
			a.isAcked = false;
			this.pendingPrepareAcks.get(messageId).put(server, a);
			Registry registry = LocateRegistry.getRegistry(server);
			ServerInterface stub = (ServerInterface) registry.lookup("compute.ServerInterface");
			stub.prepareKeyValue(messageId, functionality, key, value, serverPort);
		}catch(Exception ex){
			System.out.println(getCurrentTimeStamp() + " : Something went wrong in sending Ack, removing data from temporary storage");
		}

		System.out.println(getCurrentTimeStamp() + " : call prepare for worked. target: " + server);
	}

	public void ackMe(UUID messageId, int yourPort, Acknowledgement type) throws RemoteException{
		if (type == Acknowledgement.go){
			this.pendingGoAcks.get(messageId).get(yourPort).isAcked = true ;
		} else if (type == Acknowledgement.prepare){
			this.pendingPrepareAcks.get(messageId).get(yourPort).isAcked = true;
		}
		System.out.println(getCurrentTimeStamp() + " : Ack received from: " + yourPort);
	}

	public void go(UUID messageId, int callBackServer) throws RemoteException{

		Result res = this.pendingChanges.get(messageId);

		if (res == null){
			throw new IllegalArgumentException("The message is not in the storage");
		}

		this.KeyValue(res.function, res.key, res.value);
		this.pendingChanges.remove(messageId);
		this.sendAck(messageId, callBackServer, Acknowledgement.go);
	}

	public void prepareKeyValue(UUID messageId, String functionality, String key,
			String value, int callBackServer) throws RemoteException{

		if (this.pendingChanges.containsKey(messageId)){

			sendAck(messageId, callBackServer, Acknowledgement.prepare);
		}

		this.pendingStorageAdd(messageId, functionality, key, value);
		sendAck(messageId, callBackServer, Acknowledgement.prepare);
	}

	public void setServersInfo(int[] otherServersPorts, int yourPort) throws RemoteException {
		this.otherServers = otherServersPorts;
		this.serverPort = yourPort;
	}

	public int getPort() throws RemoteException{
		return this.serverPort;
	}

	private void sendAck(UUID messageId, int destination, Acknowledgement type){
		try{
			Registry registry = LocateRegistry.getRegistry(destination);
			ServerInterface stub = (ServerInterface) registry.lookup("compute.ServerInterface");

			stub.ackMe(messageId, serverPort, type);

		} catch(Exception ex){
			System.out.println(getCurrentTimeStamp() + " : Something went wrong in sending Ack, removing data from temporary storage");
			this.pendingChanges.remove(messageId);
		}
	}

	private void pendingStorageAdd(UUID messageId, String functionality, String key, String value) {
		Result res = new Result();
		res.function = functionality;
		res.key = key;
		res.value = value;

		this.pendingChanges.put(messageId, res);
	}

	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());
	}

}

class Result {
	String function; 
	String key;
	String value;
}

class Acknowledeged{
	public boolean isAcked;
}
