package com.practice.journalApp.service;

import com.practice.journalApp.entity.JournalEntry;
import com.practice.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        return journalEntryRepository.save(journalEntry);
    }

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

    public void deleteById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }
}
