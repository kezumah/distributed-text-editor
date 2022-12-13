package com.example.distributedtexteditor.controller;

import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
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

        // dispatch each incoming message to the main thread to broadcast
        while (true) {
            String line = in.readLine();
            if (line != null) {
                try {
                    log.logger.info(line);
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

}
