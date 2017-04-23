package com.uran.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uran.domain.Race;
import com.uran.dto.StakeDto;
import com.uran.service.RaceService;
import com.uran.service.scheduler.RaceScheduler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class StakeRestControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private RaceService raceService;
    
    @Before
    public void setUp() throws Exception {
        RaceScheduler testRaceScheduler = new RaceScheduler(null, null, null);
        Field currentRace = RaceScheduler.class.getDeclaredField("currentRace");
        currentRace.setAccessible(true);
        Race testRace = raceService.findById(4L);
        currentRace.set(testRaceScheduler, testRace);
    }
    
    @Test
    @WithMockUser(username = "User1", password = "password1", roles = "USER")
    public void shouldAddStakeFromStakeDto() throws Exception {
        String HORSE = "White Ghost:Белый призрак";
        StakeDto added = new StakeDto(1.5, HORSE);
        this.mockMvc.perform(post("/api/stakes/add")
                .with(httpBasic("user1@yandex.ru", "password1"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(added)))
                .andDo(print())
                .andExpect(status().isCreated());
        this.mockMvc.perform(get("/api/stakes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(6));
        this.mockMvc.perform(get("/api/stakes/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("stakeValue").value(1.5))
                .andExpect(jsonPath("_embedded.horse.fullName").value(HORSE));
    }
    
}