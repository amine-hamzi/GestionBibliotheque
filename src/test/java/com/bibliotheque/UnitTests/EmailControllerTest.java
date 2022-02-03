package com.bibliotheque.UnitTests;

import com.bibliotheque.controller.EmailController;
import com.bibliotheque.controller.PretController;
import com.bibliotheque.service.EmailService;
import com.bibliotheque.service.PretService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(controllers = EmailController.class)
@ActiveProfiles(profiles = {"noauth", "test"})
public class EmailControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private RepositoryRestConfiguration repositoryRestConfiguration;

    @Test
    public void testDeletePret() throws Exception {
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
