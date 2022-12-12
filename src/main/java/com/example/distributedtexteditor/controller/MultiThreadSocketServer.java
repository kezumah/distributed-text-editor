package com.example.distributedtexteditor.controller;

import com.example.distributedtexteditor.service.MessageHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A simple multithreaded socket server that dispatches a new thread for each connection and sends messages to clients
 * This class will need to be updated to handle incoming client messages through a message queue, and share the updated state of the document
 */
public class MultiThreadSocketServer extends SingleThreadSocketServer implements  Runnable{

    // List of all server threads that are handling clients
    private static ArrayList<SingleThreadSocketServer> threadList = new ArrayList<SingleThreadSocketServer>();

    // Instantiate a MessageHandler object, which will maintain the message queue and perform operational transform
    private static MessageHandler messageHandler = new MessageHandler();


    public MultiThreadSocketServer(MultiThreadSocketServer parentThread) {
        this.parentThread = parentThread;
    }

    public MultiThreadSocketServer() {
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String manageEdit(String doc, int offset){
        return messageHandler.manageEdit(doc, offset);
    }

    /**
     * Broadcast a message to every other thread, except the thread which originated the request
     * @param message
     * @param thread
     */
    public void broadcastMessage(String message, SingleThreadSocketServer thread) {
        for (int i = 0; i < threadList.size(); i++) {
            SingleThreadSocketServer currentThread = threadList.get(i);
            if (!thread.equals(currentThread)) {
                PrintWriter out;
                try {
                    out = new PrintWriter(thread.getSocket().getOutputStream(), true);
                    JSONObject content = new JSONObject();
                    content.put("content", message);
                    out.println(content.toString());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void serve() throws IOException {
        int port = 1300;
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            System.out.println("Server listening on port 1300...");

            // wait for client connection and dispatch new thread with each connection
            Socket socket = serverSocket.accept();
            MultiThreadSocketServer serverThread = new MultiThreadSocketServer(this);
            serverThread.setSocket(socket);
            threadList.add(serverThread);
            new Thread(serverThread).start();
            System.out.println("New thread dispatched for incoming request on port 1300");
        }
    }



}
