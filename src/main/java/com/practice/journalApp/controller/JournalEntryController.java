package com.practice.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JournalEntryController {

    @GetMapping("/abc")
    public String greet(){
        return "Hello";
    }
}
