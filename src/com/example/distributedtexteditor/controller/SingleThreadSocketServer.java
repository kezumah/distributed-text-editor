package com.example.distributedtexteditor.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SingleThreadSocketServer {
    private Socket socket;
    private OutputStream s1out;
    private InputStream s1In;

    OutputStreamWriter oos;

    InputStreamReader ois;

    int counter = 0;

    JSONObject message;


    public SingleThreadSocketServer(){
    }

    public void execute() throws IOException, InterruptedException {
        this.s1out = socket.getOutputStream();
        this.s1In = socket.getInputStream();
        this.oos = new OutputStreamWriter(s1out, StandardCharsets.UTF_8);
        this.ois = new InputStreamReader(s1In);

        while(true){
            counter ++;
            String value = "Message " + counter + ": This message is coming from a server thread @ pid: " + Thread.currentThread().getName();
            message = new JSONObject();
            message.put("message", value);
            sendMessageToClient(message);
            //System.out.println(message);
            //System.out.println("message sent");
            Thread.sleep(2000);
        }
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public void sendMessageToClient(JSONObject message) throws IOException {
        // Send transformed string back to client
        oos.write(message.toString());

    }

}
