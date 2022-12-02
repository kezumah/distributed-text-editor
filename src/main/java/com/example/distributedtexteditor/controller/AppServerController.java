package com.example.distributedtexteditor.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
public class AppServerController {

    /*
    This is the default test route - to be removed
     */
    @RequestMapping("/greeting")
    public String getGreeting() {
        return "Hello, world!";
    }

    /**
     Fetches the doc corresponding to a given hash from the database
     @param hash     the unique 4-letter key string that corresponds to the doc
     @return    an array representing the doc, or "ERROR" if no such doc exists in the database
     */
    @RequestMapping(value = "/doc", method = RequestMethod.POST)
    public ArrayList<String> postMethod(@RequestBody String hash) {
        ArrayList<String> doc = DatabaseController.getDocFromDatabase(hash);
        return doc;
    }

    /**
    Creates a new doc by generating a random hash and calling the database
    @return     the unique 4-letter key string that corresponds to the doc
     */
    @RequestMapping("/new")
    public String newDoc() {
        String hash = UtilityMethods.generateRandomHash();
        DatabaseController.createHashInDatabase(hash);
        return hash;
    }

}
