package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import compute.ServerInterface;

public class Client {

	public static void main(String[] args) throws Exception {	
		int[] serverPorts = new int[5];
		clientParseArgs(args, serverPorts);
		ServerInterface[] stubs = new ServerInterface[5];
		Registry[] registries = new Registry[5];

		try {
			for (int i = 0 ; i < serverPorts.length ; i++){
				registries[i] = LocateRegistry.getRegistry("LOCALHOST",serverPorts[0]);
				stubs[i] = (ServerInterface) registries[i].lookup("server.ServerInterface");  
			}

			log(stubs[0].KeyValue(UUID.randomUUID(), "PUT", "a", "1"));	    
			log(stubs[1].KeyValue(UUID.randomUUID(), "PUT", "b", "2"));	
			log(stubs[2].KeyValue(UUID.randomUUID(), "PUT", "c", "3"));	
			log(stubs[3].KeyValue(UUID.randomUUID(), "PUT", "d", "4"));	
			log(stubs[4].KeyValue(UUID.randomUUID(), "PUT", "e", "5"));	

			log(stubs[0].KeyValue(UUID.randomUUID(), "GET", "a", ""));	    
			log(stubs[1].KeyValue(UUID.randomUUID(), "GET", "b", ""));	
			log(stubs[2].KeyValue(UUID.randomUUID(), "GET", "c", ""));	
			log(stubs[3].KeyValue(UUID.randomUUID(), "GET", "d", ""));	
			log(stubs[4].KeyValue(UUID.randomUUID(), "GET", "e", ""));	


			log(stubs[0].KeyValue(UUID.randomUUID(), "DEL", "a", ""));	   
			log(stubs[4].KeyValue(UUID.randomUUID(), "DEL", "b", ""));	
			log(stubs[3].KeyValue(UUID.randomUUID(), "DEL", "c", ""));	
			log(stubs[3].KeyValue(UUID.randomUUID(), "DEL", "d", ""));	
			log(stubs[4].KeyValue(UUID.randomUUID(), "DEL", "e", ""));

			log(stubs[0].KeyValue(UUID.randomUUID(), "PUT", "1", "1"));	    
			log(stubs[1].KeyValue(UUID.randomUUID(), "PUT", "2", "2"));	
			log(stubs[2].KeyValue(UUID.randomUUID(), "PUT", "3", "3"));	
			log(stubs[3].KeyValue(UUID.randomUUID(), "PUT", "4", "4"));	
			log(stubs[4].KeyValue(UUID.randomUUID(), "PUT", "5", "5"));	

			String input = "";

			while(!input.equalsIgnoreCase("EXIT")) {
				System.out.print("Instructions need to be followed precisely ");
				System.out.print("Enter any number from 0-4 for a particular server." + "\n");
				System.out.print("PUT input format -> <SERVER No.> <PUT> <KEY> <VALUE>" +  "\n" );
				System.out.print("GET or DELETE input format -> <SERVER No.> <GET/DEL> <KEY>" + "\n");
				System.out.print("Input example -> 2 PUT 111 222 or 4 DEL 111"+ "\n");

				input = getInput();

				String[] formattedInput = input.split(" ");
				if (formattedInput.length == 3){
					log(stubs[Integer.parseInt(formattedInput[0])].KeyValue(UUID.randomUUID(), formattedInput[1], formattedInput[2], ""));
				} else if (formattedInput.length == 4){
					log(stubs[Integer.parseInt(formattedInput[0])].KeyValue(UUID.randomUUID(), formattedInput[1], formattedInput[2], formattedInput[3]));
				} else {
					System.out.println("Incorrect input format ..\n");
				}
				

			}
			for (int i = 0; i < serverPorts.length ; i++){
				System.exit(serverPorts[i]);
			}

		} catch (Exception e) {
			log(e.getMessage());
		} 
	}  

	public static String getInput() throws IOException {
		BufferedReader stringIn = new BufferedReader (new InputStreamReader(System.in));
		return  stringIn.readLine();
	}

	public static void clientParseArgs(String [] args, int[] serverPorts) throws Exception {
		if(args[0].equalsIgnoreCase("Quit"))
			System.exit(serverPorts[0]);
		else {
			if (args.length != 5) {
				String message = "Incorrect number of ports " + args.length + " Enter 5 ports";
				throw new IllegalArgumentException(message);
			} 
			for (int i = 0 ; i < args.length; i++){
				serverPorts[i] = Integer.parseInt(args[0]);
			}
		}
	}

	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());
	}

	public static void log(String message) {
		System.out.println(getCurrentTimeStamp() + ": " + message);
	}
}


