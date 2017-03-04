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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class HorseRepositoryTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @WithMockUser(username = "User1", password = "password1", roles = "USER")
    public void findByReadyTrue() throws Exception {
        this.mockMvc.perform(get("/api/horses/search/names"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("_embedded.horses", hasSize(6)))
                .andExpect(jsonPath("_embedded.horses[0].fullName").value("Black Ghost:Черный призрак"));
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void findById() throws Exception {
        this.mockMvc.perform(get("/api/horses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("name").value("Black Ghost"));
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldDeleteHorse() throws Exception {
        this.mockMvc.perform(delete("/api/horses/1")).andExpect(status().isNoContent());
        this.mockMvc.perform(get("/api/horses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(9));
    }
    
    @Test
    @WithMockUser(username = "User1", password = "password1", roles = "USER")
    public void shouldDeleteHorseForbidden() throws Exception {
        this.mockMvc.perform(delete("/api/horses/1")).andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void findAll() throws Exception {
        this.mockMvc.perform(get("/api/horses"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(10));
    }
    
}
/* this.mockMvc.perform(post("/api/races")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(horse)))
                .andExpect(status().isMethodNotAllowed());
*/
