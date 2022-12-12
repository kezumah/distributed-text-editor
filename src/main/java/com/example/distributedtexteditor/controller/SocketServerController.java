package com.example.distributedtexteditor.controller;

import java.io.IOException;

public class SocketServerController {

    public static void main(String[] args) throws IOException {
        MultiThreadSocketServer mainServerProcess = new MultiThreadSocketServer();
        mainServerProcess.serve();
    }
}
