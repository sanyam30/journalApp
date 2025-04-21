package com.practice.journalApp.service;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

//    public User updateEntry(String userName,User user) {
//
//    }

    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }
}
