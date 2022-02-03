package com.bibliotheque.IntegrationControllersTests;

import com.bibliotheque.model.Categorie;
import com.bibliotheque.repository.*;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"noauth", "test"})
public class CategorieIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private PretRepository pretRepository;

    @BeforeEach
    @AfterEach
    public void contextLoads() throws ParseException {
        pretRepository.deleteAll();
        livreRepository.deleteAll();
        clientRepository.deleteAll();
        categorieRepository.deleteAll();
    }

    @Test
    public void testGetCategories() throws Exception {
        categorieRepository.save(new Categorie("code1","informatique"));
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.categories").exists())
                .andExpect(jsonPath("_embedded.categories[*].code").isNotEmpty());
    }

    @Test
    public void testSearchByLabel() throws Exception {
        categorieRepository.save(new Categorie("code2","finance"));
        mockMvc.perform(get("/categories/search/findByLabelContaining?label=finan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.categories.[0].label", is("finance")));
    }

    @Test
    public void testUpdateCategorie() throws Exception {
        categorieRepository.save(new Categorie("code4","mathematiques"));
        JSONObject obj=new JSONObject();
        obj.put("label","mathematiq");
        Categorie categorie = categorieRepository.findByCode("code4");
        mockMvc.perform(patch("/categories/"+categorie.getId())
                .content(String.valueOf(obj)))
                .andExpect(status().is(204));
    }

    @Test
    public void testDeleteClient() throws Exception {
        categorieRepository.save(new Categorie("code5","physique"));

        Categorie categorie = categorieRepository.findByCode("code5");
        mockMvc.perform(delete("/categories/"+categorie.getId()))
                .andExpect(status().is(204));
    }

}
