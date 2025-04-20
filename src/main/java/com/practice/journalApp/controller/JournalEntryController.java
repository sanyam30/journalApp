package com.practice.journalApp.controller;

import com.practice.journalApp.JournalEntry;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    Map<Long, JournalEntry> entries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllEntries(){
        return new ArrayList<>(entries.values());
    }

    @PostMapping
    public void createEntry(@RequestBody JournalEntry entry){
        entries.put(entry.getId(),entry);
    }

    @GetMapping("/id/{id}")
    public JournalEntry getEntryById(@PathVariable Long id){
        return entries.get(id);
    }

    @PutMapping
    public void updateEntry(@RequestBody JournalEntry entry){
        entries.put(entry.getId(), entry);
    }

    @DeleteMapping("/id/{id}")
    public JournalEntry deleteEntry(@PathVariable Long id){
        return entries.remove(id);
    }


}
