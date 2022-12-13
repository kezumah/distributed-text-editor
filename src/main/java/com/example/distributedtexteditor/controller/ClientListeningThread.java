package com.example.distributedtexteditor.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * This object handles listening for messages in a client socket's input stream
 */
public class ClientListeningThread implements Runnable{

    BufferedReader in;
    public ClientListeningThread(BufferedReader in){
        this.in = in;
    }

    public void execute() throws IOException {
        while (true) {
            String line = in.readLine();
            if (line != null) {
                System.out.println(line);
            }

        }
    }

    @Override
    public void run() {
        try {
            execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
