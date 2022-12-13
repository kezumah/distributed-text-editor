package com.example.distributedtexteditor.controller;

import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


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

        counter++;

        // dispatch each incoming message to the main thread to broadcast
        while (true) {
            String line = in.readLine();
            if (line != null) {
                try {
                    //parentThread.echo();
                    parentThread.broadcastMessage(line, this);
                } catch (NullPointerException e){
                    ;
                    System.out.println("parent is null");
                }

                }
            }
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
