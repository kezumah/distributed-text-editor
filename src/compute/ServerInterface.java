package compute;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
import server.Acknowledgement;

public interface ServerInterface extends Remote {
	
	/**
	 * Processes put, get, delete based on key, value
	 */
    public String KeyValue(UUID messageId, String functionality,String key,String value) throws RemoteException;
    
    /**
     * acknowledgement of server based on acknowledgement type
     */
    public void ackMe(UUID messageId, int callBackServer, Acknowledgement type) throws RemoteException;
    
    /**
     * confirming the change to particular server.
     */
    public void go(UUID messageId,  int callBackServer) throws RemoteException;
    
    /**
     * preparing key, value for servers
     */
    public void prepareKeyValue(UUID messageId, String functionality,String key,String value, int callBackServer) throws RemoteException;
    
    /**
     * setting servers for each server.
     */
    public void setServersInfo(int[] OtherServersPorts, int ports) throws RemoteException;
    
    /**
     * get port of particular server.
     */
    public int getPort() throws RemoteException;
}
