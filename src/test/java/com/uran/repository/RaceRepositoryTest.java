package com.uran.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uran.domain.Race;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class RaceRepositoryTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    
    @Test
    @WithMockUser(username = "User1", password = "password1", roles = "USER")
    public void shouldDeleteRaceNoFound() throws Exception {
        this.mockMvc.perform(delete("/api/races/1"))
                .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldSaveRaceForbidden() throws Exception {
        Race race = new Race(null,
                LocalDateTime.of(2017, 3, 2, 10, 0),
                LocalDateTime.of(2017, 3, 2, 10, 45),
                "WhiteGhost:Белый призрак,Black Ghost:Черный призрак",
                "WhiteGhost:Белый призрак"
        );
        this.mockMvc.perform(post("/api/races")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(race)))
                .andExpect(status().isMethodNotAllowed());
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldDeleteRace() throws Exception {
        MockHttpServletResponse response = this.mockMvc.perform(get("/api/races/1"))
                .andExpect(status().isOk()).andReturn().getResponse();
        Race race = mapper.readValue(response.getContentAsString(), Race.class);
        this.mockMvc.perform(delete("/api/races/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(race)))
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/races"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(3));
    }
}