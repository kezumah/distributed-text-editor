package com.example.distributedtexteditor.controller;

import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;

public class SingleThreadSocketServer {
    private Socket socket;
    private OutputStream s1out;
    private InputStream s1In;

    PrintWriter oos;

    InputStreamReader ois;

    BufferedReader in;

    int counter = 0;
    Log log;

    public MultiThreadSocketServer parentThread;


    public SingleThreadSocketServer() throws IOException {
    }

    public void execute() throws IOException, InterruptedException {
        String threadName = Thread.currentThread().getName();
        log = new Log(threadName + "-Log.txt");
        // logger/data
        log.logger.setLevel(Level.INFO);

        this.s1out = socket.getOutputStream();
        this.s1In = socket.getInputStream();
        this.oos = new PrintWriter(s1out, true);
        this.ois = new InputStreamReader(s1In);
        this.in = new BufferedReader(ois);


        // getLock
        // unlock

        while (true) {
            counter++;
            String value = "Message " + counter + ": This message is coming from a ser thread @ pid: " + threadName;
            log.logger.info(value);
            JSONObject message = new JSONObject();
            message.put("message", value);
            sendMessageToClient(message);
            Thread.sleep(2000);
        }
        // lock
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

    public void logger() {


    }

}
