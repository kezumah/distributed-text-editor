package com.example.distributedtexteditor.controller;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestClientTwo {

    public static void main(String[] args) throws IOException, InputMismatchException, ClassNotFoundException, ParseException, InterruptedException {
        String HOST = "localhost";
        int PORT = 1300;
        System.out.println("Welcome to the chat app :) ! Enter a username to get started:");
        Scanner obj = new Scanner(System.in);
        String userName = obj.nextLine();
        TestClient testClient = new TestClient(HOST, PORT, userName);
    }
}
