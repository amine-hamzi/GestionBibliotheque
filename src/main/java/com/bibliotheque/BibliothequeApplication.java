package com.bibliotheque;

import com.bibliotheque.model.Categorie;
import com.bibliotheque.model.Client;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Pret;
import com.bibliotheque.repository.CategorieRepository;
import com.bibliotheque.repository.ClientRepository;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.PretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BibliothequeApplication implements CommandLineRunner {

    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private ClientRepository  clientRepository;
    @Autowired
    private LivreRepository livreRepository;
    @Autowired
    private PretRepository pretRepository;

    public static void main(String[] args) {
        SpringApplication.run(BibliothequeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        categorieRepository.save(new Categorie("code1","informatique"));
        livreRepository.save(new Livre("isbn1", "title1", new Date(), 10, "auteur1", categorieRepository.findByCode("code1")));
        clientRepository.save(new Client("hamzi", "amine", "developpeur informatique", "hay jawadi rue 39 N15","hamzimohammedhamzi@gmail.com", new Date()));
        pretRepository.save(new Pret(new Date(), new Date(), clientRepository.findByEmail("hamzimohammedhamzi@gmail.com"), livreRepository.findByIsbn("isbn1")));

        categorieRepository.save(new Categorie("code2","finance"));
        livreRepository.save(new Livre("isbn2", "title2", new Date(), 10, "auteur2", categorieRepository.findByCode("code2")));
        clientRepository.save(new Client("hamzi", "mostapha", "comptable", "hay jawadi rue 39 N15","hamzimostapha@gmail.com", new Date()));
        pretRepository.save(new Pret(new Date(), new Date(), clientRepository.findByEmail("hamzimostapha@gmail.com"), livreRepository.findByIsbn("isbn2")));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date1 = "22/06/2006";
        Date date = simpleDateFormat.parse(date1);

        categorieRepository.save(new Categorie("code3","comptabilite"));
        livreRepository.save(new Livre("isbn3", "title3", new Date(), 10, "auteur3", categorieRepository.findByCode("code3")));
        clientRepository.save(new Client("guelsa", "mouna", "comptable", "hay jawadi rue 39 N15","guelsamouna@gmail.com", new Date()));
        pretRepository.save(new Pret(date, new Date(), clientRepository.findByEmail("guelsamouna@gmail.com"), livreRepository.findByIsbn("isbn3")));


        categorieRepository.save(new Categorie("code4","mathematiques"));
        livreRepository.save(new Livre("isbn4", "title4", new Date(), 10, "auteur4", categorieRepository.findByCode("code4")));
        clientRepository.save(new Client("hamzi", "abdo", "developpeur informatique", "hay jawadi rue 39 N15","hamziabdo@gmail.com", new Date()));
        pretRepository.save(new Pret(new Date(), new Date(), clientRepository.findByEmail("hamziabdo@gmail.com"), livreRepository.findByIsbn("isbn4")));

        String date2 = "22/06/2008";
        Date dateDebut = simpleDateFormat.parse(date2);

        categorieRepository.save(new Categorie("code5","physique"));
        livreRepository.save(new Livre("isbn5", "title5", new Date(), 10, "auteur5", categorieRepository.findByCode("code5")));
        clientRepository.save(new Client("hamzi", "asmaa", "comptable", "hay jawadi rue 39 N15","hamziasmaa@gmail.com", new Date()));
        pretRepository.save(new Pret(new Date(), new Date(), clientRepository.findByEmail("hamziasmaa@gmail.com"), livreRepository.findByIsbn("isbn5")));
        pretRepository.save(new Pret(dateDebut, new Date(), clientRepository.findByEmail("hamziabdo@gmail.com"), livreRepository.findByIsbn("isbn4")));

        categorieRepository.save(new Categorie("code6","Biologie"));




        /*System.out.println("*****************************");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String date1 = "22/06/2006";
        Date date = simpleDateFormat.parse(date1);

        Optional<Pret> pret = pretRepository.findById(Long.valueOf(17));
        //pret.get().setClient(clientRepository.getById(Long.valueOf(2)));
        //pretRepository.save(pret.get());


        //Pret pret = pretRepository.getById(Long.valueOf(19));
        //pretRepository.delete(pret);
        System.out.println("******************************************************");*/
    }
}
