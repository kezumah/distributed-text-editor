package com.example.distributedtexteditor.controller;


import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
import java.util.InputMismatchException;

public class TestClient {

    private String host;
    private int port;
    private OutputStream s1out;
    private InputStream s1In;

    private Socket s1;

    int TIMEOUT = 2000;


    public TestClient(String host, int port) throws IOException, ClassNotFoundException, ParseException {

        this.host = host;
        this.port = port;

        // Open connection to a server at the server port and set timeout
        s1 = new Socket(host, port);
        //s1.setSoTimeout(TIMEOUT);

        // Get a communication stream associated with the socket
        s1out = s1.getOutputStream();
        s1In = s1.getInputStream();

        OutputStreamWriter oos = new OutputStreamWriter(s1out);
        InputStreamReader ois = new InputStreamReader(s1In);
        // JSONParser jsonParser = new JSONParser(ois);

        while(true){
            // Continuously print messages received from server
           // int incomingMessage = ois.read();
            JSONParser jsonParser = new JSONParser(ois);
            System.out.println("message read");

            JSONObject jsonObject = (JSONObject)jsonParser.parse();
            System.out.println("Message received from server: " + jsonObject);

        }

    }


    public static void main(String[] args) throws IOException, InputMismatchException, ClassNotFoundException, ParseException {
        String HOST = "localhost";
        int PORT = 1300;
        TestClient testClient = new TestClient(HOST, PORT);
    }
}
