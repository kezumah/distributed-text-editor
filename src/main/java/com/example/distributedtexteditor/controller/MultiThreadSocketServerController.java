package com.example.distributedtexteditor.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            System.out.println("Server listening on port 1000...");

            // wait for client connection and dispatch new thread with each connection
            Socket socket = serverSocket.accept();

            System.out.println("New thread dispatched for incoming request on port 1000");
            MultiThreadSocketServerController serverThread = new MultiThreadSocketServerController();
            serverThread.setSocket(socket);
            new Thread(serverThread).start();

        }

    }

}
