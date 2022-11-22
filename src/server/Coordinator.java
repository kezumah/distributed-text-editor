package server;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import compute.ServerInterface;

public class Coordinator extends Thread {

	static Server[] servers = new Server[5];
	
	 public static void main(String args[]) throws Exception{   	   	
		 	int[] serverPorts = new int[5];
	    	serverParseArgs(args, serverPorts);
	    	for (int i = 0 ; i < servers.length ; i++)
	    	{
	    		try{
	    			servers[i] = new Server();
	    			ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject((Remote) servers[i], 0);
	    			Registry registry = LocateRegistry.createRegistry(serverPorts[i]); 
	    		    registry.bind("compute.ServerInterface", stub);
	    		    giveNewServerInfo(serverPorts, serverPorts[i]);
	    		    log(String.format("Server %s is running at port %s", 
	    		    		Integer.toString(i), Integer.toString(serverPorts[i])));
	    		    
	    		} catch (Exception e) {
	    		    System.err.println("Server exception: " + e.toString());
				}
	    		
			    Thread serverThread=new Thread();
			    serverThread.start();
	    	}
	    }

	private static void giveNewServerInfo(int[] servers, int port) {
		try{
			Registry registry = LocateRegistry.getRegistry(port);
		    
		    ServerInterface stub = (ServerInterface) registry.lookup("compute.ServerInterface");
		    
		    int j = 0;
		    int[] other = new int[servers.length -1];
		    for (int i = 0 ; i < servers.length ; i++)
		    {
		    	if (servers[i] != port)
		    	{
		    		other[j] = servers[i];
		    		j++;
		    	}
		    }
		    		
		    stub.setServersInfo(other, port);
		}
		catch(Exception ex)
		{
			log("Cannot connect to server!" + port);
			log(ex.getMessage());
		}
	}
	
	public static void serverParseArgs(String[] args, int[] serverPorts) throws Exception {
	 	  if (args.length < 5) {
	 	 	 String message =  "Please specify Port Numbers ";
	 	 	 throw new IllegalArgumentException(message);
	 	 }
	 	 for (int i = 0 ; i < args.length ; i++)
	 	 {
	 		serverPorts[i] = Integer.parseInt(args[i]);
	 	 }
		
	 }
	 
	 public static String getCurrentTimeStamp() {
	 	 return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());
	 }
	 
	 public static void log(String message) {
	 	  System.out.println(getCurrentTimeStamp() + ": " + message);
	 }
	 
	 public static void log(String format, Object[] objs){
		 System.out.println(String.format(format, objs));
	 }
}
