package com.example.distributedtexteditor.entity;


import com.example.distributedtexteditor.repository.Database;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseImpl extends java.rmi.server.UnicastRemoteObject implements Database {

    HashMap<String, ArrayList<String>> database;
    String defaultString = "This is the default text in the document";
    ArrayList<String> defaultDoc;
    String defaultHash = "abcd";

    public DatabaseImpl() throws RemoteException {
        super();
        // initialize db with one document inside
        this.database = new HashMap<String, ArrayList<String>>();
        defaultDoc = new ArrayList<String>();
        for (char c : defaultString.toCharArray()) {
            defaultDoc.add("" + c);
        }
        database.put(defaultHash, defaultDoc);

    }

    @Override
    public void createHashInDatabase(String hash) throws RemoteException {
        ArrayList<String> newDoc = new ArrayList<String>();
        newDoc.add("");
        database.put(hash, newDoc);
    }

    @Override
    public ArrayList<String> getDocFromDatabase(String hash) throws RemoteException {
        ArrayList<String> doc = database.get(hash);
        System.out.println(hash);
        System.out.println(hash.getClass());
        if (doc != null){
            return doc;
        } else {
            ArrayList<String> newDoc = new ArrayList<String>();
            newDoc.add("No hash in DB for this doc :(");
            return newDoc;
        }
    }

    @Override
    public void saveDoc(String hash, ArrayList<String> doc) throws RemoteException {
        database.put(hash, doc);
    }
}
