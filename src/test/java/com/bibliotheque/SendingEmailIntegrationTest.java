package com.bibliotheque;

import com.bibliotheque.model.Client;
import com.bibliotheque.repository.ClientRepository;
import com.bibliotheque.service.Email;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SendingEmailIntegrationTest {

    private String token="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTk4ODU2Miwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.KXWga9c0CFf5Gn-t40d9czto723OUYhggkg8zT48DZWClbg_VoYXQ9JFsAhhbHzPuPDZ4TNAhdb4P-aaDTCY4Q";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSendingEmail() throws Exception {
        JSONObject obj=new JSONObject();
        obj.put("toEmail","hamzimohammedhamzi@gmail.com");
        obj.put("body","integration test worked");
        obj.put("subject","integration test");
        mockMvc.perform(post("/send").header("Authorization" , token)
        .content(String.valueOf(obj))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200));
    }



}
