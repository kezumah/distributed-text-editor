package com.example.distributedtexteditor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.distributedtexteditor.entity.Doc;
import com.example.distributedtexteditor.entity.Documents;
import com.example.distributedtexteditor.entity.User;
import com.example.distributedtexteditor.repository.DocumentRepository;
import com.example.distributedtexteditor.service.DatabaseService;
import com.example.distributedtexteditor.service.UserService;
import com.example.distributedtexteditor.service.UtilityMethods;

@RestController
public class AppServerController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private DatabaseService databaseController;
	
	/*
    This is the default test route - to be removed
     */
    @GetMapping("/greeting")
    public String getGreeting() {
        return "Hello, world!";
    }

    /**
     Fetches the doc corresponding to a given hash from the database
     @param hash     the unique 4-letter key string that corresponds to the doc
     @return    an array representing the doc, or "ERROR" if no such doc exists in the database
     */
    @RequestMapping(value = "/saveDoc", method = RequestMethod.POST)
    public Documents postMethod(@RequestParam String hash, @RequestParam (value = "docs") List<String> docs) {
        Documents doc = databaseController.saveDocument(hash, docs);
        return doc;
    }

    /**
    Creates a new doc by generating a random hash and calling the database
    @return     the unique 4-letter key string that corresponds to the doc
     */
    @PostMapping("/new")
    public String newDoc() {
        String hash = UtilityMethods.generateRandomHash();
        databaseController.createHashInDatabase(hash);
        return hash;
    }
    
    @GetMapping(value = "/alldocs")
    public List<Documents> getAllDocs() {
    	return databaseController.findAllDocs();
    }
    
    @GetMapping(value = "/docsByHash")
    public List<String> getDocById(@RequestParam String hash) {
    	return databaseController.getDocFromDatabase(hash);
    }
    
    /*

	@PostMapping("/addUser")
	public User addUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PostMapping("/addUsers")
	public List<User> addUsers(@RequestBody List<User> users) {
		return userService.createUsers(users);
	}

	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getUsers();
	}
	
	@PutMapping("/updateuser")
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@DeleteMapping("/user/{id}")
	public String deleteUser(@PathVariable int id) {
		return userService.deleteUserById(id);
	}
	
	*/
}
