package com.example.distributedtexteditor.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple multithreaded socket server that dispatches a new thread for each connection and sends messages to clients
 * This class will need to be updated to handle incoming client messages through a message queue, and share the updated state of the document
 */
public class MultiThreadSocketServerController extends SingleThreadSocketServer implements  Runnable{


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

    public static void main(String[] args) throws IOException {
        int port = 1300;

        ServerSocket serverSocket = new ServerSocket(port);

        while(true){
            System.out.println("Server listening on port 1300...");

            // wait for client connection and dispatch new thread with each connection
            Socket socket = serverSocket.accept();

            System.out.println("New thread dispatched for incoming request on port 1300");
            MultiThreadSocketServerController serverThread = new MultiThreadSocketServerController();
            serverThread.setSocket(socket);
            new Thread(serverThread).start();

        }

    }

}
