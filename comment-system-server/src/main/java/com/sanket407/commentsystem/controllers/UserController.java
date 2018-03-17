package com.sanket407.commentsystem.controllers;

import com.sanket407.commentsystem.entity.User;
import com.sanket407.commentsystem.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController
{
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping()
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
     
    @GetMapping("{username}")
    public User getUser(@PathVariable String username,
                        HttpServletResponse response) throws IOException
    {
        User user = null;
        
        try {
            user = userRepository.getOne(username);
        }
        catch (EntityNotFoundException e)
        {
            response.sendError(404, "Requested User not found");
            return null;
        }
        return user;
    }
    
    
    @PostMapping("new")
    public User addNewUser(@RequestBody User newUser,
                           HttpServletResponse response) throws IOException
    {   
       boolean alreadyExists = userRepository.existsById(newUser.getUsername());
          
        if(alreadyExists)
        {
            response.sendError(409, "Username already exists");
            return null;
        }
        
        return userRepository.saveAndFlush(newUser);
    }
    
    @DeleteMapping()
    public void deleteUser(@PathVariable User newUser,
                           HttpServletResponse response) throws IOException
    {
        User user = userRepository.getOne(newUser.getUsername());
        
        if(user == null)
        {
            response.sendError(409, "User doesnt exist");
            return;
        }
        
       userRepository.delete(user);
    }
    
    @PostMapping("authenticate")
    public User authenticateUser(@RequestBody User user,
                                 HttpServletResponse response) throws IOException
    {
        boolean doesUserExist = userRepository.existsById(user.getUsername());
        if(!doesUserExist)
        {
            response.sendError(401, "Invalid username");
            return null;
        }
        
        User storedUser = userRepository.getOne(user.getUsername());
        
        boolean isPassWordCorrect = user.getPassword().equals(storedUser.getPassword());
        if(!isPassWordCorrect)
        {
            response.sendError(401, "Invalid password");
            return null;
        }
        
        return storedUser;
    }
}
