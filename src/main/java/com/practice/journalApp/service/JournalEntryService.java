package com.practice.journalApp.service;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public JournalEntry updateEntry(ObjectId id,JournalEntry entry) {
        JournalEntry oldEntry = journalEntryRepository.findById(id).orElse(null);
        if(oldEntry!=null){
            oldEntry.setTitle(entry.getTitle()!=null && !entry.getTitle().isEmpty() ? entry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(entry.getContent()!=null && !entry.getContent().isEmpty() ? entry.getContent() : oldEntry.getContent());
            journalEntryRepository.save(oldEntry);
        }
        return oldEntry;
    }

    @Transactional
    public void deleteById(ObjectId id, String username) {
        try {
            User user = userService.findByUserName(username);
            boolean removed = user.getJournalEntryList().removeIf(entry -> entry.getId().equals(id));
            if (removed) {
                journalEntryRepository.deleteById(id);
                userService.saveUser(user);
            }
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error occured while removing entry",e);
        }
    }

    @Transactional
    public void saveEntry(JournalEntry entry, String username) {
        User user = userService.findByUserName(username);
        entry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(entry);
        user.getJournalEntryList().add(saved);
        userService.saveUser(user);
    }
}
