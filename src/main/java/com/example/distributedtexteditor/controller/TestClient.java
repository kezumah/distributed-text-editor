package com.example.distributedtexteditor.controller;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TestClient {

    private String host;
    private int port;
    private OutputStream s1out;
    private InputStream s1In;

    private Socket s1;

    private PrintWriter oos;

    int TIMEOUT = 2000;

    private Scanner userInputScanner;

    private String userName;

    private static ZoneId TIMEZONE = ZoneId.of("America/Los_Angeles");

    public TestClient(String host, int port, String userName) throws IOException, ClassNotFoundException, ParseException, InterruptedException {

        this.host = host;
        this.port = port;
        this.userName = userName;


        // Open connection to a server at the server port and set timeout
        try {
            s1 = new Socket(host, port);
            //s1.setSoTimeout(TIMEOUT);

            // Get a communication stream associated with the socket
            s1out = s1.getOutputStream();
            s1In = s1.getInputStream();

            this.oos = new PrintWriter(s1out, true);
            InputStreamReader ois = new InputStreamReader(s1In);
            BufferedReader in = new BufferedReader(ois);

            // create and send off a new thread to handle listening
            ClientListeningThread listener = new ClientListeningThread(in);

            new Thread(listener).start();

            // continuously consume and send user input
            while (true) {
                userInputScanner = new Scanner(System.in);
                String message = userInputScanner.nextLine();
                String messageWithHeader = ZonedDateTime.now(TIMEZONE) + " " + userName + ": " + message;
                System.out.println(messageWithHeader);
                oos.println(messageWithHeader);
            }
        } catch (Exception e) {
            // Reconnect failed, wait.
            System.out.println("Connection failed. Ensure server is available then retry.");
            }
        }



    public static void main(String[] args) throws IOException, InputMismatchException, ClassNotFoundException, ParseException, InterruptedException {
        String HOST = "localhost";
        int PORT = 1300;
        System.out.println("Welcome to the chat app :) ! Enter a username to get started:");
        Scanner obj = new Scanner(System.in);
        String userName = obj.nextLine();
        TestClient testClient = new TestClient(HOST, PORT, userName);
    }
}
