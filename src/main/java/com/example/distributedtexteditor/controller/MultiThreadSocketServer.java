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
    private static ArrayList<MultiThreadSocketServer> threadList = new ArrayList<MultiThreadSocketServer>();

    // Instantiate a MessageHandler object, which will maintain the message queue and perform operational transform
    private static MessageHandler messageHandler = new MessageHandler();

    // A reference to the main thread
    //private MultiThreadSocketServer parentThread;

    public MultiThreadSocketServer(MultiThreadSocketServer parentThread) {
        this.parentThread = parentThread;
    }

    public MultiThreadSocketServer() {
    }

    public void echo(){
        System.out.println("This is the parent thread!");
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

    /**
     * Broadcast a message to every other thread, except the thread which originated the request
     * @param message
     * @param thread
     */
    public synchronized void broadcastMessage(String message, SingleThreadSocketServer thread) {
        //System.out.println("About to broadcast from parent" + message);
        for (int i = 0; i < threadList.size(); i++) {
            MultiThreadSocketServer currentThread = threadList.get(i);
            if (!thread.equals(currentThread)) {
                //System.out.println("not current thread");
                PrintWriter out;
                try {
                    out = new PrintWriter(currentThread.getSocket().getOutputStream(), true);
                    out.println(message);
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
            Thread newThreadRef = new Thread(serverThread);
            threadList.add(serverThread);
            newThreadRef.start();
            System.out.println("New thread dispatched for incoming request on port 1300");
        }
        //            serverThread.setSocket(socket);
        //            threadList.add(serverThread);
        //            new Thread(serverThread).start();
    }



}
