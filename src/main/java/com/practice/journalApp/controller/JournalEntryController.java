package com.practice.journalApp.controller;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.service.JournalEntryService;
import com.practice.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("{username}")
    public ResponseEntity<?> getAllEntriesOfUser(@PathVariable String username){
        User user = userService.findByUserName(username);
        List<JournalEntry> allEntries = user.getJournalEntryList();
        return allEntries!=null && !allEntries.isEmpty() ? new ResponseEntity<>(allEntries, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry, @PathVariable String username){
        try{
            journalEntryService.saveEntry(entry,username);
            return new ResponseEntity<>(entry,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id){
        Optional<JournalEntry> entry = journalEntryService.findById(id);
        if(entry.isPresent())
            return new ResponseEntity<>(entry.get(),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{username}/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id, @PathVariable String username, @RequestBody JournalEntry entry){
        JournalEntry journalEntry = journalEntryService.updateEntry(id, entry);
        return journalEntry!=null ? new ResponseEntity<>(journalEntry,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id, @PathVariable String username){
        journalEntryService.deleteById(id,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
