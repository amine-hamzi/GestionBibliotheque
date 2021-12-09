package com.bibliotheque.IntegrationControllersTests;

import com.bibliotheque.model.Categorie;
import com.bibliotheque.model.Client;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Pret;
import com.bibliotheque.repository.CategorieRepository;
import com.bibliotheque.repository.ClientRepository;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.PretRepository;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("noauth")
public class PretIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    PretRepository pretRepository;

    @Autowired
    private ClientRepository  clientRepository;

    @Autowired
    private LivreRepository livreRepository;

    @BeforeEach
    @AfterEach
    public void contextLoads() throws ParseException {
        pretRepository.deleteAll();
        livreRepository.deleteAll();
        clientRepository.deleteAll();
        categorieRepository.deleteAll();
    }

    @Test
    public void testGetPrets() throws Exception {
        categorieRepository.save(new Categorie("code1","informatique"));
        livreRepository.save(new Livre("isbn1", "title1", new Date(), 10, "auteur1", categorieRepository.findByCode("code1")));
        clientRepository.save(new Client("hamzi", "amine", "developpeur informatique", "hay jawadi rue 39 N15","hamzimohammedhamzi@gmail.com", new Date()));
        pretRepository.save(new Pret(new Date(), new Date(), clientRepository.findByEmail("hamzimohammedhamzi@gmail.com"), livreRepository.findByIsbn("isbn1")));

        mockMvc.perform(get("/prets"))
                .andExpect(jsonPath("_embedded.prets").exists())
                .andExpect(jsonPath("_embedded.prets[*].dateFin").isNotEmpty());
    }

    @Test
    public void testSearchByClient() throws Exception {
        categorieRepository.save(new Categorie("code2","finance"));
        livreRepository.save(new Livre("isbn2", "title2", new Date(), 10, "auteur2", categorieRepository.findByCode("code2")));
        clientRepository.save(new Client("hamzi", "mostapha", "comptable", "hay jawadi rue 39 N15","hamzimostapha@gmail.com", new Date()));
        pretRepository.save(new Pret(new Date(), new Date(), clientRepository.findByEmail("hamzimostapha@gmail.com"), livreRepository.findByIsbn("isbn2")));

        Long id = clientRepository.findByEmail("hamzimostapha@gmail.com").getId();

        mockMvc.perform(get("/prets/search/findByClient?client=/clients/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.prets").exists())
                .andExpect(jsonPath("_embedded.prets[*].dateFin").isNotEmpty());
    }

    @Test
    public void testSearchByDateDebut() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date1 = "22/06/2006";
        Date date = simpleDateFormat.parse(date1);

        categorieRepository.save(new Categorie("code3","comptabilite"));
        livreRepository.save(new Livre("isbn3", "title3", new Date(), 10, "auteur3", categorieRepository.findByCode("code3")));
        clientRepository.save(new Client("guelsa", "mouna", "comptable", "hay jawadi rue 39 N15","guelsamouna@gmail.com", new Date()));
        pretRepository.save(new Pret(date, new Date(), clientRepository.findByEmail("guelsamouna@gmail.com"), livreRepository.findByIsbn("isbn3")));

        mockMvc.perform(get("/prets/search/findByDateDebutLessThanEqual?date=2007-08-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.prets").exists())
                .andExpect(jsonPath("_embedded.prets[*].dateFin").isNotEmpty());
    }

    @Test
    public void testUpdatePret() throws Exception {
        categorieRepository.save(new Categorie("code4","mathematiques"));
        livreRepository.save(new Livre("isbn4", "title4", new Date(), 10, "auteur4", categorieRepository.findByCode("code4")));
        clientRepository.save(new Client("hamzi", "abdo", "developpeur informatique", "hay jawadi rue 39 N15","hamziabdo@gmail.com", new Date()));
        pretRepository.save(new Pret(new Date(), new Date(), clientRepository.findByEmail("hamziabdo@gmail.com"), livreRepository.findByIsbn("isbn4")));

        JSONObject obj=new JSONObject();
        obj.put("dateFin","2019-02-18");

        Pret pret = pretRepository.findByClient(clientRepository.findByEmail("hamziabdo@gmail.com")).get(0);

        mockMvc.perform(patch("/prets/"+pret.getId())
                .content(String.valueOf(obj)))
                .andExpect(status().is(204));
    }

    @Test
    public void testDeletePret() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date2 = "22/06/2008";
        Date dateDebut = simpleDateFormat.parse(date2);
        categorieRepository.save(new Categorie("code5","physique"));
        livreRepository.save(new Livre("isbn5", "title5", new Date(), 10, "auteur5", categorieRepository.findByCode("code5")));
        clientRepository.save(new Client("hamzi", "asmaa", "comptable", "hay jawadi rue 39 N15","hamziasmaa@gmail.com", new Date()));
        pretRepository.save(new Pret(dateDebut, new Date(), clientRepository.findByEmail("hamziasmaa@gmail.com"), livreRepository.findByIsbn("isbn5")));

        Pret pret = pretRepository.findByDateDebut(dateDebut);
        mockMvc.perform(delete("/prets/"+pret.getId()))
                .andExpect(status().is(200));
    }

}
