package com.bibliotheque;

import com.bibliotheque.model.Categorie;
import com.bibliotheque.model.Client;
import com.bibliotheque.repository.CategorieRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategorieIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CategorieRepository categorieRepository;

    @Test
    public void testGetCategories() throws Exception {
        //categorieRepository.save(new Categorie("code1","informatique"));
        mockMvc.perform(get("/categories").header("Authorization" , "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA1NjUyMywicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.XGdU3pS02q2JSEoijTPsmJ3zGDS5jFXvjUplnizJy7_BbyFMuNKOJ_5Kb7EOc517ht8zLICiVC4c1hc6ZSMzPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.categories").exists())
                .andExpect(jsonPath("_embedded.categories[*].code").isNotEmpty());
    }

    @Test
    public void testSearchByLabel() throws Exception {
        //categorieRepository.save(new Categorie("code2","finance"));
        mockMvc.perform(get("/categories/search/findByLabelContaining?label=finan").header("Authorization" , "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA1NjUyMywicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.XGdU3pS02q2JSEoijTPsmJ3zGDS5jFXvjUplnizJy7_BbyFMuNKOJ_5Kb7EOc517ht8zLICiVC4c1hc6ZSMzPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.categories.[0].label", is("finance")));
    }

    @Test
    public void testUpdateCategorie() throws Exception {
        //categorieRepository.save(new Categorie("code4","mathematiques"));
        JSONObject obj=new JSONObject();
        obj.put("label","mathematiq");
        Categorie categorie = categorieRepository.findByCode("code4");
        mockMvc.perform(patch("/categories/"+categorie.getId()).header("Authorization" , "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA1NjUyMywicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.XGdU3pS02q2JSEoijTPsmJ3zGDS5jFXvjUplnizJy7_BbyFMuNKOJ_5Kb7EOc517ht8zLICiVC4c1hc6ZSMzPA")
                .content(String.valueOf(obj)))
                .andExpect(status().is(204));
    }

    @Test
    public void testDeleteClient() throws Exception {
        //categorieRepository.save(new Categorie("code5","physique"));

        Categorie categorie = categorieRepository.findByCode("code6");
        mockMvc.perform(delete("/categories/"+categorie.getId()).header("Authorization" , "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTA1NjUyMywicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.XGdU3pS02q2JSEoijTPsmJ3zGDS5jFXvjUplnizJy7_BbyFMuNKOJ_5Kb7EOc517ht8zLICiVC4c1hc6ZSMzPA"))
                .andExpect(status().is(204));
    }

}
