package com.recoder.capstone.service.user;

import com.recoder.capstone.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(User user);
    User getUserByEmail(String email);
}
