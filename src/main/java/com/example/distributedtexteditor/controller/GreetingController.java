package com.example.distributedtexteditor.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
public class GreetingController {

    @RequestMapping("/")
    public String getGreeting() {
        return "Hello, world!!!";
    }

    @RequestMapping("/query")
    public String getQuery() {
        return "Query!!!";
    }

}
