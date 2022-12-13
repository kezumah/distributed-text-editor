package com.example.distributedtexteditor.controller;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

//import com.example.distributedtexteditor.entity.Documents;
import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import com.example.distributedtexteditor.entity.Documents;
//import com.example.distributedtexteditor.entity.User;
//import com.example.distributedtexteditor.repository.DocumentRepository;
//import com.example.distributedtexteditor.service.DatabaseService;
//import com.example.distributedtexteditor.service.UserService;
import com.example.distributedtexteditor.service.UtilityMethods;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AppServerController {

    /*
    This is the default test route - to be removed
     */
    @GetMapping("/greeting")
    public String getGreeting() {
        System.out.println("request received");
        JSONObject response = new JSONObject();
        response.put("value", "Hello, testing!");
        return "Hello, testing!";
    }

    /**
     * Fetches the doc corresponding to a given hash from the database
     *
     * @param hash the unique 4-letter key string that corresponds to the doc
     * @return an array representing the doc, or "ERROR" if no such doc exists in the database
     */
    @RequestMapping(value = "/doc", method = RequestMethod.POST)
    public ArrayList<String> postMethod(@RequestBody String hash) throws MalformedURLException, NotBoundException, RemoteException {
        System.out.println(hash);
        System.out.println("/doc route hit");
        ArrayList<String> doc = DatabaseController.getDocFromDatabase(hash);
        return doc;
    }

    /**
     * Creates a new doc by generating a random hash and calling the database
     *
     * @return the unique 4-letter key string that corresponds to the doc
     */
    @RequestMapping("/new")
    public String newDoc() throws MalformedURLException, NotBoundException, RemoteException {
        String hash = UtilityMethods.generateRandomHash();
        DatabaseController.createHashInDatabase(hash);
        return hash;
    }
}
