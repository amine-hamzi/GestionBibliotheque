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

    private String token="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTk4ODU2Miwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.KXWga9c0CFf5Gn-t40d9czto723OUYhggkg8zT48DZWClbg_VoYXQ9JFsAhhbHzPuPDZ4TNAhdb4P-aaDTCY4Q";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void testGetClients() throws Exception {
        //clientRepository.save(new Client("hamzi", "amine", "developpeur informatique", "hay jawadi rue 39 N15","hamzimohammedhamzi@gmail.com", new Date()));
        mockMvc.perform(get("/clients").header("Authorization" , token))
                .andExpect(jsonPath("_embedded.clients").exists())
                .andExpect(jsonPath("_embedded.clients[*].nom").isNotEmpty());
    }

    @Test
    public void testSearchByNom() throws Exception {
        //clientRepository.save(new Client("hamzi", "mostapha", "comptable", "hay jawadi rue 39 N15","hamzimostapha@gmail.com", new Date()));
        mockMvc.perform(get("/clients/search/findByNomContaining?nom=hamzi").header("Authorization" , token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.clients.[0].nom", is("hamzi")));
    }

    @Test
    public void testSearchByEmail() throws Exception {
        //clientRepository.save(new Client("guelsa", "mouna", "comptable", "hay jawadi rue 39 N15","guelsamouna@gmail.com", new Date()));
        mockMvc.perform(get("/clients/search/findByEmail?email=guelsamouna@gmail.com").header("Authorization" , token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("guelsamouna@gmail.com")));
    }

    @Test
    public void testUpdateClient() throws Exception {
        //clientRepository.save(new Client("hamzi", "abdo", "developpeur informatique", "hay jawadi rue 39 N15","hamziabdo@gmail.com", new Date()));
        JSONObject obj=new JSONObject();
        obj.put("prenom","abd");
        Client client = clientRepository.findByEmail("hamziabdo@gmail.com");
        mockMvc.perform(patch("/clients/"+client.getId()).header("Authorization" , token)
                .content(String.valueOf(obj)))
                .andExpect(status().is(204));
    }

    @Test
    public void testDeleteClient() throws Exception {
        //clientRepository.save(new Client("hamzi", "asmaa", "comptable", "hay jawadi rue 39 N15","hamziasmaa@gmail.com", new Date()));
        Client client = clientRepository.findByEmail("hamziasmaa@gmail.com");
        mockMvc.perform(delete("/clients/"+client.getId()).header("Authorization" , token))
                .andExpect(status().is(204));
    }




}
