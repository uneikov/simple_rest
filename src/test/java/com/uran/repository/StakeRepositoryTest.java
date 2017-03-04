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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void findByUserId() throws Exception {
       
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
    public void shouldReturnStakesAsPage() throws Exception {
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
    public void findByHorseIdAndRaceId() throws Exception {
        
    }
    
    @Test
    public void findByRaceIdAndUserId() throws Exception {
        
    }
    
    @Test
    public void findByDateTime() throws Exception {
        
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
    public void findByEditableTrue() throws Exception {
        
    }
    
    @Test
    public void findByRaceIdAndWinsTrue() throws Exception {
        
    }
    
}