package com.restapi.karthik.services;


import com.restapi.karthik.entities.User;
import com.restapi.karthik.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();
       return  userRepository.findById(Math.toIntExact(userId)).orElse(null);
    }
}
