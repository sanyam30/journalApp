package com.practice.journalApp.controller;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.service.JournalEntryService;
import com.practice.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllEntriesOfUser(){
        User user = userService.findByUserName(getUsername());
        List<JournalEntry> allEntries = user.getJournalEntryList();
        return allEntries!=null && !allEntries.isEmpty() ? new ResponseEntity<>(allEntries, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry){
        try{
            journalEntryService.saveEntry(entry,getUsername());
            return new ResponseEntity<>(entry,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id){
        User user = userService.findByUserName(getUsername());
        boolean found = user.getJournalEntryList().stream().anyMatch(x -> x.getId().equals(id));
        if(found){
            Optional<JournalEntry> entry = journalEntryService.findById(id);
            if(entry.isPresent())
                return new ResponseEntity<>(entry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry entry) {
        String userName = getUsername();
        User user = userService.findByUserName(getUsername());
        boolean found = user.getJournalEntryList().stream().anyMatch(x -> x.getId().equals(id));
        if (found) {
            JournalEntry journalEntry = journalEntryService.updateEntry(id, entry);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id){
        journalEntryService.deleteById(id,getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
