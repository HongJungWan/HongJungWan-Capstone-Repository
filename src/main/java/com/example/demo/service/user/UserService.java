package com.example.demo.service.user;

import com.example.demo.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(User user);
    User getUserByEmail(String Email);


}
