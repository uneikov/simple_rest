package com.uran.service.simulation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RaceSimulationHelperImplTest {
    @Autowired
    private RaceSimulationHelper helper;
    
    @Test
    public void getHorsesForRace() throws Exception {
        Assert.assertTrue(this.helper.getHorsesForRace().size() == 6);
    }
    
    @Test
    public void createBots() throws Exception {
        
    }
    
    @Test
    public void initBots() throws Exception {
        
    }
    
    @Test
    public void fillAccounts() throws Exception {
        
    }
    
    @Test
    public void clearAccounts() throws Exception {
        
    }
    
    @Test
    public void killBots() throws Exception {
        
    }
    
    @Test
    public void startGamble() throws Exception {
        
    }
    
}