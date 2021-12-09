package com.bibliotheque.IntegrationServicesTests;


import com.bibliotheque.controller.PretForm;
import com.bibliotheque.model.Categorie;
import com.bibliotheque.model.Client;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Pret;
import com.bibliotheque.repository.CategorieRepository;
import com.bibliotheque.repository.ClientRepository;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.service.PretService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
@Transactional
public class PretServiceTest {

    @Autowired
    private PretService pretService;

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
    public void deletePretTest(){
        //arrange
        Categorie categorie = categorieRepository.save(new Categorie("1","informatique"));
        Livre livre = livreRepository.save(new Livre("n1", "title1", new Date(), 10, "auteur1", categorie));
        Client client = clientRepository.save(new Client("hamzi", "amine", "developpeur informatique", "hay jawadi rue 39 N15","mohammedhamzi@gmail.com", new Date()));
        Pret pret = pretRepository.save(new Pret(new Date(), new Date(), client, livre));

        //when
        String res = this.pretService.deletePret(pret.getId());

        //then
        assertThat(res).isEqualTo(" ");
    }

    @Test
    public void savePretTest() throws ParseException {
        //arrange
        Categorie categorie = categorieRepository.save(new Categorie("2","infor"));
        Livre livre = livreRepository.save(new Livre("n2", "title1", new Date(), 10, "auteur1", categorie));
        Client client = clientRepository.save(new Client("hamzi", "amine", "developpeur informatique", "hay jawadi rue 39 N15","medhamzi@gmail.com", new Date()));
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        String date2 = "04/09/2021";
        Date dateDebut = simpleDateFormat1.parse(date2);
        String date3 = "04/09/2022";
        Date dateFin = simpleDateFormat1.parse(date3);
        Pret pret = pretRepository.save(new Pret(dateDebut, dateFin, client, livre));
        PretForm pretForm = new PretForm(dateDebut, dateFin, "http://localhost:8088/prets/"+pret.getId()+"/client/"+client.getId(),"http://localhost:8088/prets/"+pret.getId()+"/livre/"+livre.getId());

        //when
        String res = this.pretService.savePret(pretForm);

        //then
        assertThat(res).isEqualTo(" ");
    }
}
