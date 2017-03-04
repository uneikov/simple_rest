package com.uran.service;

import com.uran.domain.User;
import com.uran.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();
    User findOne(long id);
    User save(User user);
    List<User> save(List<User> userList);
    void delete(long id);
    User findByEmail(String email);
    void enable(long id, boolean enabled);
    void update(UserDto userDto);
    void update(User user);
}
