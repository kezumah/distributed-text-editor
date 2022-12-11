package com.example.distributedtexteditor.controller;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
import java.util.InputMismatchException;

public class TestClientTwo {

    public static void main(String[] args) throws IOException, InputMismatchException, ClassNotFoundException, ParseException {
        String HOST = "localhost";
        int PORT = 1300;
        TestClient testClient = new TestClient(HOST, PORT);
    }
}
