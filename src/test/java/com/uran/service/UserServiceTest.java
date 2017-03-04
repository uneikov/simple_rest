package com.uran.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {
    
    @Autowired
    private UserService userService;
    @Autowired
    private StakeService stakeService;
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldReturnAllUsers() throws Exception {
        Assert.assertTrue(this.userService.findAll().size() == 4);
    }
    
    @Test
    @Transactional
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldDeleteUser() throws Exception {
        this.userService.delete(1);
        Assert.assertTrue(this.userService.findAll().size() == 3);
        Assert.assertTrue(this.stakeService.findAll().size() == 2);
    }
}