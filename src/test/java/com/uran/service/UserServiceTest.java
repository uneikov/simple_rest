package com.uran.service;

import com.uran.domain.Account;
import com.uran.domain.Role;
import com.uran.domain.User;
import com.uran.util.RandomUtil;
import com.uran.util.user.UserUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private StakeService stakeService;
    
    @Test
    public void shouldReturnAllUsers() throws Exception {
        Assert.assertTrue(this.userService.findAll().size() == 4);
    }
    
    @Test
    public void shouldDeleteUser() throws Exception {
        this.userService.delete(1);
        Assert.assertTrue(this.userService.findAll().size() == 3);
        Assert.assertTrue(this.stakeService.findAll().size() == 2);
    }

    @Test
    //@WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldSaveUser() throws Exception {
        User testUser = new User(
                        null,
                        "testUser",
                        "testUser@test.com",
                        "1234",
                        true,
                        null,
                        Collections.singleton(Role.ROLE_USER)
        );
        testUser.setAccount(new Account(RandomUtil.getRandomCardNumber(), 10.0, testUser));
        this.userService.save(UserUtil.prepareToSave(testUser));
        Assert.assertTrue(this.userService.findAll().size() == 5);
        Assert.assertTrue(this.userService.findOne(5L).getName().equals("testUser"));
        Assert.assertTrue(this.userService.findOne(5L).getAccount().getBalance() == 10.0);
    }

}