package com.example.distributedtexteditor.controller;

import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class SingleThreadSocketServer {
    private Socket socket;
    private OutputStream s1out;
    private InputStream s1In;

    PrintWriter oos;

    InputStreamReader ois;

    BufferedReader in;

    int counter = 0;

    public MultiThreadSocketServer parentThread;


    public SingleThreadSocketServer(){
    }

    public void execute() throws IOException, InterruptedException {
        this.s1out = socket.getOutputStream();
        this.s1In = socket.getInputStream();
        this.oos = new PrintWriter(s1out, true);
        this.ois = new InputStreamReader(s1In);
        this.in = new BufferedReader(ois);

        while (true) {
            counter++;
            String value = "Message " + counter + ": This message is coming from a server thread @ pid: " + Thread.currentThread().getName();
            JSONObject message = new JSONObject();
            message.put("message", value);
            sendMessageToClient(message);
            Thread.sleep(2000);
        }


        /*
        while (true) {
            String line = in.readLine();
            // Handle client messages here and then send them to the main thread's message queue
            if (line != null) {
                JSONObject json = new JSONObject(line);
                System.out.println("Received: " + json);
                System.out.println(json.get("message"));
                //parentThread.manageEdit("", 2);
            }
           */
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket(){
        return this.socket;
    }

    public void sendMessageToClient(JSONObject message) throws IOException {
        // Send transformed string back to client
        oos.println(message.toString());

    }

}
