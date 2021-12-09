package com.bibliotheque.IntegrationControllersTests;

import com.bibliotheque.model.Categorie;
import com.bibliotheque.model.Livre;
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
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("noauth")
public class LivreIntegrationTest {


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
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    @AfterEach
    public void contextLoads() throws ParseException {
        pretRepository.deleteAll();
        livreRepository.deleteAll();
        clientRepository.deleteAll();
        categorieRepository.deleteAll();
    }

    @Test
    public void testGetLivres() throws Exception {
        categorieRepository.save(new Categorie("code1","informatique"));
        livreRepository.save(new Livre("isbn1", "title1", new Date(), 10, "auteur1", categorieRepository.findByCode("code1")));
        mockMvc.perform(get("/livres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.livres").exists())
                .andExpect(jsonPath("_embedded.livres[*].isbn").isNotEmpty());
    }

    @Test
    public void testSearchByTitle() throws Exception {
        categorieRepository.save(new Categorie("code2","finance"));
        livreRepository.save(new Livre("isbn2", "title2", new Date(), 10, "auteur2", categorieRepository.findByCode("code2")));
        mockMvc.perform(get("/livres/search/findByTitleContaining?titre=title2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.livres.[0].title", is("title2")));
    }

    @Test
    public void testSearchByIsbn() throws Exception {
        categorieRepository.save(new Categorie("code3","comptabilite"));
        livreRepository.save(new Livre("isbn3", "title3", new Date(), 10, "auteur3", categorieRepository.findByCode("code3")));
        mockMvc.perform(get("/livres/search/findByIsbn?isbn=isbn3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("title3")));
    }

    @Test
    public void testUpdateClient() throws Exception {
        categorieRepository.save(new Categorie("code4","mathematiques"));
        livreRepository.save(new Livre("isbn4", "title4", new Date(), 10, "auteur4", categorieRepository.findByCode("code4")));
        JSONObject obj=new JSONObject();
        obj.put("auteur","auteur");
        Livre livre = livreRepository.findByIsbn("isbn4");
        mockMvc.perform(patch("/livres/"+livre.getId())
                .content(String.valueOf(obj)))
                .andExpect(status().is(204));
    }

    @Test
    public void testDeleteClient() throws Exception {
        categorieRepository.save(new Categorie("code5","physique"));
        livreRepository.save(new Livre("isbn5", "title5", new Date(), 10, "auteur5", categorieRepository.findByCode("code5")));
        Livre livre = livreRepository.findByIsbn("isbn5");
        mockMvc.perform(delete("/livres/"+livre.getId()))
                .andExpect(status().is(204));
    }

}
