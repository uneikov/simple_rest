package com.uran.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class StakeRepositoryTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void findByUserId() throws Exception {
        this.mockMvc.perform(
                get("/api/stakes/search/findByUserId?userId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("_embedded.stakes").exists())
                .andExpect(jsonPath("page.totalElements").value(3));
    }
    
    @Test
    @WithMockUser(username = "User1", password = "password1", roles = "USER")
    public void shouldReturnStakesAsJson() throws Exception {
        this.mockMvc.perform(get("/api/stakes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(5));
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldReturnStakesByRaceIdAsPage() throws Exception {
        this.mockMvc.perform(get("/api/stakes/search/findByRaceId?raceId=1&page=0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(1));
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldReturnStakesAsList() throws Exception {
        this.mockMvc.perform(get("/api/stakes/search/getListByRaceId?raceId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("_embedded.stakes").isArray());
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldDeleteStakeAndCascade() throws Exception {
        this.mockMvc.perform(delete("/api/stakes/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/api/stakes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(4));
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldReturnStakeByHorseIdAndRaceId() throws Exception {
        this.mockMvc.perform(
                get("/api/stakes/search/findByHorseIdAndRaceId?horseId=4&raceId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("_embedded.stakes").exists());
    }
    
    @Test
    public void findByRaceIdAndUserId() throws Exception {
        
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void findByDateTime() throws Exception {
        this.mockMvc.perform(
                get("/api/stakes/search/findByDateTime?dateTime=2016-05-30T10:00:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("_embedded.stakes").exists());
    }
    
    @Test
    public void findByDateTimeBefore() throws Exception {
        
    }
    
    @Test
    public void findByDateTimeBetween() throws Exception {
        
    }
    
    @Test
    public void findByDateTimeBetweenAndWinsIs() throws Exception {
        
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void findByEditableTrue() throws Exception {
        this.mockMvc.perform(
                get("/api/stakes/search/findByEditableTrue"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("_embedded.stakes").exists());
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void findByRaceIdAndWinsTrue() throws Exception {
        this.mockMvc.perform(
                get("/api/stakes/search/findByRaceIdAndWinsTrue?raceId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("_embedded.stakes").exists());
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void getAllCashByRaceId() throws Exception {
        this.mockMvc.perform(
                get("/api/stakes/cash/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json;charset=UTF-8"))
                .andExpect(content().string("200.5"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void testSetUneditable() throws Exception {
        this.mockMvc.perform(post("/api/stakes/search/setEditableByRaceId?raceId=4"))
                .andDo(print());
        /*this.mockMvc.perform(get("/api/stakes/search/getListByRaceId?raceId=4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("_embedded.stakes").exists());*/
    }
}