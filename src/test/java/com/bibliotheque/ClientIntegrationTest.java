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
public class ClientIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void testGetClients() throws Exception {
        //clientRepository.save(new Client("hamzi", "amine", "developpeur informatique", "hay jawadi rue 39 N15","hamzimohammedhamzi@gmail.com", new Date()));
        mockMvc.perform(get("/clients").header("Authorization" , "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA1NjUyMywicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.XGdU3pS02q2JSEoijTPsmJ3zGDS5jFXvjUplnizJy7_BbyFMuNKOJ_5Kb7EOc517ht8zLICiVC4c1hc6ZSMzPA"))
                .andExpect(jsonPath("_embedded.clients").exists())
                .andExpect(jsonPath("_embedded.clients[*].nom").isNotEmpty());
    }

    @Test
    public void testSearchByNom() throws Exception {
        //clientRepository.save(new Client("hamzi", "mostapha", "comptable", "hay jawadi rue 39 N15","hamzimostapha@gmail.com", new Date()));
        mockMvc.perform(get("/clients/search/findByNomContaining?nom=hamzi").header("Authorization" , "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA1NjUyMywicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.XGdU3pS02q2JSEoijTPsmJ3zGDS5jFXvjUplnizJy7_BbyFMuNKOJ_5Kb7EOc517ht8zLICiVC4c1hc6ZSMzPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.clients.[0].nom", is("hamzi")));
    }

    @Test
    public void testSearchByEmail() throws Exception {
        //clientRepository.save(new Client("guelsa", "mouna", "comptable", "hay jawadi rue 39 N15","guelsamouna@gmail.com", new Date()));
        mockMvc.perform(get("/clients/search/findByEmail?email=guelsamouna@gmail.com").header("Authorization" , "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA1NjUyMywicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.XGdU3pS02q2JSEoijTPsmJ3zGDS5jFXvjUplnizJy7_BbyFMuNKOJ_5Kb7EOc517ht8zLICiVC4c1hc6ZSMzPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("guelsamouna@gmail.com")));
    }

    @Test
    public void testUpdateClient() throws Exception {
        //clientRepository.save(new Client("hamzi", "abdo", "developpeur informatique", "hay jawadi rue 39 N15","hamziabdo@gmail.com", new Date()));
        JSONObject obj=new JSONObject();
        obj.put("prenom","abd");
        Client client = clientRepository.findByEmail("hamziabdo@gmail.com");
        mockMvc.perform(patch("/clients/"+client.getId()).header("Authorization" , "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA1NjUyMywicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.XGdU3pS02q2JSEoijTPsmJ3zGDS5jFXvjUplnizJy7_BbyFMuNKOJ_5Kb7EOc517ht8zLICiVC4c1hc6ZSMzPA")
                .content(String.valueOf(obj)))
                .andExpect(status().is(204));
    }

    @Test
    public void testDeleteClient() throws Exception {
        //clientRepository.save(new Client("hamzi", "asmaa", "comptable", "hay jawadi rue 39 N15","hamziasmaa@gmail.com", new Date()));
        Client client = clientRepository.findByEmail("hamziasmaa@gmail.com");
        mockMvc.perform(delete("/clients/"+client.getId()).header("Authorization" , "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA1NjUyMywicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.XGdU3pS02q2JSEoijTPsmJ3zGDS5jFXvjUplnizJy7_BbyFMuNKOJ_5Kb7EOc517ht8zLICiVC4c1hc6ZSMzPA"))
                .andExpect(status().is(204));
    }




}
