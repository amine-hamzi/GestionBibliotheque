package com.bibliotheque;

import com.bibliotheque.model.Categorie;
import com.bibliotheque.model.Client;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.CategorieRepository;
import com.bibliotheque.repository.ClientRepository;
import com.bibliotheque.repository.LivreRepository;
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
public class LivreIntegrationTest {

    private String token="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMTk4ODU2Miwicm9sZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.KXWga9c0CFf5Gn-t40d9czto723OUYhggkg8zT48DZWClbg_VoYXQ9JFsAhhbHzPuPDZ4TNAhdb4P-aaDTCY4Q";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    LivreRepository livreRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @Test
    public void testGetLivres() throws Exception {
        //categorieRepository.save(new Categorie("code1","informatique"));
        //livreRepository.save(new Livre("isbn1", "title1", new Date(), 10, "auteur1", categorieRepository.findByCode("code1")));
        mockMvc.perform(get("/livres").header("Authorization" , token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.livres").exists())
                .andExpect(jsonPath("_embedded.livres[*].isbn").isNotEmpty());
    }

    @Test
    public void testSearchByTitle() throws Exception {
        //categorieRepository.save(new Categorie("code2","finance"));
        //livreRepository.save(new Livre("isbn2", "title2", new Date(), 10, "auteur2", categorieRepository.findByCode("code2")));
        mockMvc.perform(get("/livres/search/findByTitleContaining?titre=title2").header("Authorization" , token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.livres.[0].title", is("title2")));
    }

    @Test
    public void testSearchByIsbn() throws Exception {
        //categorieRepository.save(new Categorie("code3","comptabilite"));
        //livreRepository.save(new Livre("isbn3", "title3", new Date(), 10, "auteur3", categorieRepository.findByCode("code3")));
        mockMvc.perform(get("/livres/search/findByIsbn?isbn=isbn3").header("Authorization" , token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("title3")));
    }

    @Test
    public void testUpdateClient() throws Exception {
        //categorieRepository.save(new Categorie("code4","mathematiques"));
        //livreRepository.save(new Livre("isbn4", "title4", new Date(), 10, "auteur4", categorieRepository.findByCode("code4")));
        JSONObject obj=new JSONObject();
        obj.put("auteur","auteur");
        Livre livre = livreRepository.findByIsbn("isbn4");
        mockMvc.perform(patch("/livres/"+livre.getId()).header("Authorization" , token)
                .content(String.valueOf(obj)))
                .andExpect(status().is(204));
    }

    @Test
    public void testDeleteClient() throws Exception {
        //categorieRepository.save(new Categorie("code5","physique"));
        //livreRepository.save(new Livre("isbn5", "title5", new Date(), 10, "auteur5", categorieRepository.findByCode("code5")));
        Livre livre = livreRepository.findByIsbn("isbn5");
        mockMvc.perform(delete("/livres/"+livre.getId()).header("Authorization" , token))
                .andExpect(status().is(204));
    }

}
