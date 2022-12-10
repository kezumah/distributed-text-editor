package com.example.distributedtexteditor.controller;

import java.io.*;
import java.net.Socket;

public class SingleThreadSocketServer {
    private Socket socket;
    private OutputStream s1out;
    private InputStream s1In;

    ObjectOutputStream oos;

    ObjectInputStream ois;

    int counter = 0;

    String message;

    public SingleThreadSocketServer(){
    }

    public void execute() throws IOException, InterruptedException {
        this.s1out = socket.getOutputStream();
        this.s1In = socket.getInputStream();
        this.oos = new ObjectOutputStream(s1out);
        this.ois = new ObjectInputStream(s1In);

        while(true){
            counter ++;
            message = "Message " + counter + ": This message is coming from a server thread @ pid: " + Thread.currentThread().getName();
            sendMessageToClient(message);
            Thread.sleep(2000);
        }
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public void sendMessageToClient(String message) throws IOException {
        // Send transformed string back to client
        oos.writeObject(message);

    }

}
