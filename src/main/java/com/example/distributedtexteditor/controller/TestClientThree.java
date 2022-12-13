package com.example.distributedtexteditor.controller;


import org.apache.tomcat.util.json.ParseException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestClientThree {

    public static void main(String[] args) throws IOException, InputMismatchException, ClassNotFoundException, ParseException, InterruptedException {
        String HOST = "localhost";
        int PORT = 1300;
        System.out.println("Welcome to the chat app :) ! Enter a username to get started:");
        Scanner obj = new Scanner(System.in);
        String userName = obj.nextLine();
        TestClient testClient = new TestClient(HOST, PORT, userName);
    }
}
