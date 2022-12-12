package com.example.distributedtexteditor;

import com.example.distributedtexteditor.entity.DatabaseImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SpringBootApplication
public class DistributedTextEditorApplication {
	private static Registry registry;
	private static int databasePort = 2000;

	public static void main(String[] args) throws RemoteException {
		// start app and create remote database at port 2000
		SpringApplication.run(DistributedTextEditorApplication.class, args);
		registry = LocateRegistry.createRegistry(databasePort);
		registry.rebind("/DBServer", new DatabaseImpl());
	}

}
