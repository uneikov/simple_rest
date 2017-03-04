package com.uran.service.simulation;


import com.uran.domain.Account;
import com.uran.domain.Role;
import com.uran.domain.User;
import com.uran.service.AccountService;
import com.uran.service.UserService;
import com.uran.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service("botFactoryService")
class BotFactoryService {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    
    List<User> getBots(int size) {
        
        List<User> users = IntStream
                .range(0, size)
                .mapToObj(i -> new User(
                        null,
                        "testuser" + i,
                        "testuser" + i + "@test.com",
                        "testpass" + i,
                        true,
                        null,
                        Collections.singleton(Role.ROLE_USER)
                ))
                .collect(Collectors.toList());
    
        return userService.save(users).stream()
                .peek(user -> {
                    user.setAccount(accountService.save(new Account(
                            RandomUtil.getRandomCardNumber(),
                            0.0d,
                            user
                    )));
                    userService.save(user);
                }).collect(Collectors.toList());
    }
}
