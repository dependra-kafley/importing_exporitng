package com.dependra.importing_exporitng.service;

import com.dependra.importing_exporitng.dao.UserRepository;
import com.dependra.importing_exporitng.model.User;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
    public void saveListUser(List<User> userList){
        userRepository.saveAll(userList);
    }

    public void updateLocation(User user,String loc){
        user.setFileLocation(loc);
        saveUser(user);
    }
    public User getUserByid(int id){
        return userRepository.getById(id);
    }

    public String getFileLocation(int id){
        return userRepository.getFileLocation(id);
    }
}
