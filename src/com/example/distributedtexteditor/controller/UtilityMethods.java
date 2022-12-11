package com.example.distributedtexteditor.controller;

import java.nio.charset.Charset;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class UtilityMethods {

    /*
    Generates random string of length 4
     */
    public static String generateRandomHash() {
        int HASH_LENGTH = 4;
        String randomHash = "";
        Random randomGenerator = new Random();
        char letter;
        for (int i = 0; i < HASH_LENGTH; i++) {
            letter = (char)(randomGenerator.nextInt(26) + 'a');
            randomHash = randomHash + letter;
        }
        return randomHash;
    }
}
