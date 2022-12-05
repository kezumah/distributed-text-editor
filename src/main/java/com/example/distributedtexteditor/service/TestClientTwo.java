package com.example.distributedtexteditor.controller;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;

public class TestClientTwo {

    private String host;
    private int port;
    private OutputStream s1out;
    private InputStream s1In;
    private Socket s1;

    int TIMEOUT = 2000;


    public TestClientTwo(String host, int port) throws IOException, ClassNotFoundException {

        this.host = host;
        this.port = port;

        // Open connection to a server at the server port and set timeout
        s1 = new Socket(host, port);
        //s1.setSoTimeout(TIMEOUT);

        // Get a communication stream associated with the socket
        s1out = s1.getOutputStream();
        s1In = s1.getInputStream();

        ObjectOutputStream oos = new ObjectOutputStream(s1out);
        ObjectInputStream ois = new ObjectInputStream(s1In);

        while(true){
            // Continuously print messages received from server
            String incomingMessage = (String) ois.readObject();
            System.out.println("Message received from server: " + incomingMessage);

        }

    }


    public static void main(String[] args) throws IOException, InputMismatchException, ClassNotFoundException {
        String HOST = "localhost";
        int PORT = 1000;
        TestClientTwo testClient = new TestClientTwo(HOST, PORT);
    }
}
