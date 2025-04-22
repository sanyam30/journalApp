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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveNewUser(User user){
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        System.out.println("raw password: "+user.getPassword());
        System.out.println("encoded password: "+encodedPassword);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

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

    public void deleteUserByName(String username) {
        userRepository.deleteByUserName(username);
    }
}
