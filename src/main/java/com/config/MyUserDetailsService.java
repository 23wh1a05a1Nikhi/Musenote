package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.dao.UserRegRepository;
import com.model.UserReg;

import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRegRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserReg> user = userRepo.findByName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Returning Spring Security compatible UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.get().getUserName(), user.get().getPassword(), Collections.emptyList()
        );
    }
}