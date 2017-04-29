package com.uran.service;

import com.uran.domain.Race;
import com.uran.domain.Stake;
import com.uran.service.scheduler.RaceScheduler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("postgres")
public class StakeServiceTest {
    //private static final Logger LOG = LoggerFactory.getLogger(StakeServiceTest.class);
    
    @Autowired
    private StakeService service;

    @Autowired private HorseService horseService;

    @Autowired private UserService userService;

    @Autowired private RaceService raceService;

    @Before
    public void setUp() throws Exception {
        RaceScheduler testRaceScheduler = new RaceScheduler(null, null, null);
        Field currentRace = RaceScheduler.class.getDeclaredField("currentRace");
        currentRace.setAccessible(true);
        Race testRace = raceService.findById(4L);
        currentRace.set(testRaceScheduler, testRace);
    }

    @Test
    public void findAll() throws Exception {
        Assert.assertTrue(this.service.findAll().size() == 5);
    }
    
    @Test
    public void findById() throws Exception {
        Assert.assertTrue(this.service.findById(1L).getAmount() == 10.0);
    }
    
    @Test
    public void findAllByRaceId() throws Exception {
        Assert.assertTrue(this.service.findByRaceId(4L).size() == 2);
    }
    
    @Test
    public void findAllByUserId() throws Exception {
        Assert.assertTrue(this.service.findAllByUserId(1L, new PageRequest(0, 10))
                .getTotalElements() == 3);
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
        this.service.save(new Stake(
                null,
                this.userService.findOne(1L),
                this.horseService.findById(1L),
                RaceScheduler.getCurrentRace(),
                15.35)
        );
        Assert.assertTrue(this.service.findAll().size() == 6);
    }
    
    @Test
    public void update() throws Exception {
        Stake stake = this.service.findById(2L);
        stake.setStakeValue(15.35);
        this.service.update(stake);
        Assert.assertTrue(this.service.findById(2L).getStakeValue() == 15.35);
    }
    
    @Test
    public void setWinningStakes() throws Exception {
        this.service.setWinningStakes(4L, 4L);
        Assert.assertTrue(this.service.getWinningStakes(4L).size() == 2);
    }
    
    @Test
    public void getWinningStakes() throws Exception {
        Assert.assertTrue(this.service.getWinningStakes(1L).size() == 1);
    }
    
    @Test
    public void processWinningStakes() throws Exception {
        
    }
    
    @Test
    public void getAllCash() throws Exception {
        Double allCash = this.service.getAllCash(4L);
        Assert.assertEquals(200.5, allCash, 0.001);
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