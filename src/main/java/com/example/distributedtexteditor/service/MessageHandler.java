package com.example.distributedtexteditor.service;

import org.json.JSONObject;

import java.util.ArrayList;

public class MessageHandler {

    public ArrayList<JSONObject> messageQueue;

    public MessageHandler(){
        this.messageQueue = new ArrayList<JSONObject>();
    }

    public String manageEdit(String docName, int version){
        return "";
    }


}
