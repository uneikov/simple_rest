package com.uran.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uran.dto.UserDto;
import com.uran.util.card.RandomCreditCardNumberGenerator;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser(username = "Admin", password = "admin", roles = "ADMIN")
    public void shouldAddUserFromUserDto() throws Exception {
        String cardNumber = RandomCreditCardNumberGenerator.generateVisaCardNumber();
        UserDto added = new UserDto(null, cardNumber,"testUser", "test@test.com", "000007");
        this.mockMvc.perform(post("/api/users/add")
                .with(httpBasic("admin@gmail.com", "admin"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(added)))
                .andDo(print())
                .andExpect(status().isCreated());
        this.mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("page.totalElements").value(5));
        this.mockMvc.perform(get("/api/accounts/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("cardNumber").value(cardNumber));
    }
}
