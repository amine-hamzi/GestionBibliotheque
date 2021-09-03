package com.bibliotheque;

import com.bibliotheque.model.Client;
import com.bibliotheque.repository.ClientRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogin() throws Exception {
        JSONObject obj=new JSONObject();
        obj.put("username","admin");
        obj.put("password","1234");
        mockMvc.perform(post("/login").content(String.valueOf(obj)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"));
    }




}
