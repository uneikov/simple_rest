package com.uran.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uran.dto.HorseDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class HorseRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldAddHorseFromHorseDto() throws Exception {
        HorseDto added = new HorseDto(null,"Tornado", "Торнадо", 3);
        this.mockMvc.perform(post("/api/horses/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(added)))
                .andDo(print())
                .andExpect(status().isCreated());
        this.mockMvc.perform(get("/api/horses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(11));
    }
    
}