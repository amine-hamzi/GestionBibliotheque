package com.bibliotheque.IntegrationControllersTests;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("noauth")
public class SendingEmailIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSendingEmail() throws Exception {
        JSONObject obj=new JSONObject();
        obj.put("toEmail","hamzimohammedhamzi@gmail.com");
        obj.put("body","integration test worked");
        obj.put("subject","integration test");
        mockMvc.perform(post("/send")
        .content(String.valueOf(obj))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(200));
    }



}
