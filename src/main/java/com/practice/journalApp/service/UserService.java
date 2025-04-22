package com.practice.journalApp.service;

import com.practice.journalApp.entity.User;
import com.practice.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public void deleteUserByName(String username) {
        userRepository.deleteByUserName(username);
    }
}
