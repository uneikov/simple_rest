package com.uran.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uran.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static com.uran.domain.Role.ROLE_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class UserRepositoryTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldReturnUserByEmailIgnoringCase() throws Exception {
        this.mockMvc.perform(get("/api/users/search/findByEmailIgnoringCase?email=admin@gmail.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("name").value("Admin"));
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldReturnUserByName() throws Exception {
        this.mockMvc.perform(get("/api/users/search/findByName?name=Admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("email").value("admin@gmail.com"));
    }
    
    @Test
    @WithMockUser(username = "User1", password = "password1", roles = "USER")
    public void shouldReturnUserByNameForbidden() throws Exception {
        this.mockMvc.perform(get("/api/users/search/findByName?name=Admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldReturnAllUsers() throws Exception {
        this.mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(4));
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldReturnUser() throws Exception {
        this.mockMvc.perform(get("/api/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("name").value("User1"))
                .andExpect(jsonPath("roles").value("ROLE_USER"));
        
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldDeleteUser() throws Exception {
        this.mockMvc.perform(delete("/api/users/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/api/stakes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(2));
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldCreateUser() throws Exception {
        User added = new User(null, "new", "new@new.com", "passnew", null, ROLE_USER);
        ResultActions resultActions = this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(added)))
                .andDo(print())
                .andExpect(status().isCreated());
        User user = mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), User.class);
        Assert.assertEquals(added.toString(), user.toString());
    }
    
    @Test
    @WithMockUser(username = "User1", password = "password1", roles = "USER")
    public void shouldReturnDeleteForbidden() throws Exception {
        this.mockMvc.perform(delete("/api/users/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    
}