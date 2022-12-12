package com.example.distributedtexteditor.repository;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Database extends Remote {

    public void createHashInDatabase(String hash) throws RemoteException;

    public ArrayList<String> getDocFromDatabase(String hash) throws RemoteException;

    public  void saveDoc (String hash, ArrayList<String> doc) throws RemoteException;

}
