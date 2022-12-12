package com.example.distributedtexteditor.controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.example.distributedtexteditor.entity.DatabaseImpl;
import com.example.distributedtexteditor.repository.Database;
import org.springframework.stereotype.Service;

@Service
public class DatabaseController {

    public static void createHashInDatabase(String hash) throws MalformedURLException, NotBoundException, RemoteException {
        Database databaseStub = (Database) Naming.lookup("rmi://localhost:" + "2000" + "/DBServer");
        databaseStub.createHashInDatabase(hash);
    }

    public static ArrayList<String> getDocFromDatabase(String hash) throws MalformedURLException, NotBoundException, RemoteException {
        Database databaseStub = (Database) Naming.lookup("rmi://localhost:" + "2000" + "/DBServer");
        ArrayList<String> doc = databaseStub.getDocFromDatabase(hash);
        return doc;
    }

    public static void saveDoc (String hash, ArrayList<String> doc) throws MalformedURLException, NotBoundException, RemoteException {
        Database databaseStub = (Database) Naming.lookup("rmi://localhost:" + "2000" + "/DBServer");
        databaseStub.saveDoc(hash, doc);
    }
}
