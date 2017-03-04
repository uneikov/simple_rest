package com.uran.service;

import com.uran.domain.Race;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RaceServiceTest {
    
    @Autowired
    private RaceService service;
    
    @Test
    public void findAll() throws Exception {
        
    }
    
    @Test
    public void findAll1() throws Exception {
        
    }
    
    @Test
    public void findById() throws Exception {
        
    }
    
    @Test
    //@WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldSaveRace() throws Exception {
        this.service.save(new Race(null,
                LocalDateTime.of(2017, 3, 2, 10, 0),
                LocalDateTime.of(2017, 3, 2, 10, 45),
                "WhiteGhost:Белый призрак,Black Ghost:Черный призрак",
                "WhiteGhost:Белый призрак"
        ));
        Assert.assertTrue(this.service.findAll().size() == 5);
    }
    
    @Test
    public void update() throws Exception {
        
    }
    
}