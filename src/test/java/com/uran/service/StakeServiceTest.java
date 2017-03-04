package com.uran.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StakeServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(StakeServiceTest.class);
    
    @Autowired
    private StakeService service;
    
    @Test
    public void findAll() throws Exception {
        
    }
    
    @Test
    public void findById() throws Exception {
        
    }
    
    @Test
    public void findAllByRaceId() throws Exception {
        
    }
    
    @Test
    public void findAllByUserId() throws Exception {
        
    }
    
    @Test
    public void findAllByRaceIdAndUserId() throws Exception {
        
    }
    
    @Test
    public void addStake() throws Exception {
        
    }
    
    @Test
    public void delete() throws Exception {
        this.service.delete(1L);
        Assert.assertTrue(this.service.findAll().size() == 4);
    }
    
    @Test
    public void save() throws Exception {
        
    }
    
    @Test
    public void update() throws Exception {
        
    }
    
    @Test
    public void setWinningStakes() throws Exception {
        
    }
    
    @Test
    public void getWinningStakes() throws Exception {
        
    }
    
    @Test
    public void processWinningStakes() throws Exception {
        
    }
    
    @Test
    public void getAllCash() throws Exception {
        final Double allCash = this.service.getAllCash(4L);
        Assert.assertEquals(allCash, 200.5, 0.001);
    }
    
    @Test
    public void setNotEditable() throws Exception {
        
    }
    
    @Test
    public void setUneditable() throws Exception {
        this.service.setUneditable(4L);
        this.service.findByRaceId(4L).forEach(s -> Assert.assertTrue(!s.isEditable()));
    }
    
}