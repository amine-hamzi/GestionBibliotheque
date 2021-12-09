package com.bibliotheque.UnitTests;


import com.bibliotheque.controller.PretForm;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.ClientRepository;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.service.Email;
import com.bibliotheque.service.EmailServiceImpl;
import com.bibliotheque.service.PretService;
import com.bibliotheque.service.PretServiceImpl;
import net.bytebuddy.dynamic.DynamicType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class PretServiceTest {

    @InjectMocks
    private PretServiceImpl pretService ;

    @MockBean
    private LivreRepository livreRepository;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private EmailServiceImpl emailService;

    @MockBean
    private PretRepository pretRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @Test
    public void deletePret(){
        //arrange
        Categorie categorie = new Categorie("code1","informatique");
        Livre livre = new Livre("isbn1", "title1", new Date(), 10, "auteur1", categorie);
        Client client = new Client("hamzi", "amine", "developpeur informatique", "hay jawadi rue 39 N15","hamzimohammedhamzi@gmail.com", new Date());
        Pret pret = new Pret(new Date(), new Date(), client, livre);

        when(pretRepository.getById(Long.valueOf(4))).thenReturn(pret);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(livreRepository.save(any(Livre.class))).thenReturn(livre);
        when(reservationRepository.findByLivre(any(Livre.class))).thenReturn(new ArrayList<Reservation>());
        doNothing().when(pretRepository).deleteById(any(Long.class));
        when(emailService.sendSimpleEmail(any(Email.class))).thenReturn(true);


        //when
        String res = pretService.deletePret(Long.valueOf(4));

        //then
        assertThat(res).isEqualTo(" ");

    }

    @Test
    public void savePret() throws ParseException {
        //arrange
        Categorie categorie = new Categorie("code2","informatique");
        Livre livre = new Livre("isbn2", "title1", new Date(), 10, "auteur1", categorie);
        Client client = new Client("hamzi", "amine", "developpeur informatique", "hay jawadi rue 39 N15","hamzimohammedhamzi@gmail.com", new Date());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        String date2 = "04/09/2021";
        Date dateDebut = simpleDateFormat1.parse(date2);
        String date3 = "04/09/2022";
        Date dateFin = simpleDateFormat1.parse(date3);
        Pret pret = new Pret(dateDebut, dateFin, client, livre);

        when(clientRepository.save(any(Client.class))).thenReturn(client);
        when(livreRepository.save(any(Livre.class))).thenReturn(livre);
        when(pretRepository.save(any(Pret.class))).thenReturn(pret);
        when(livreRepository.getById(any(Long.class))).thenReturn(livre);
        when(clientRepository.getById(any(Long.class))).thenReturn(client);
        when(clientRepository.getById(null)).thenReturn(client);
        when(livreRepository.getById(null)).thenReturn(livre);


        PretForm pretForm = new PretForm(dateDebut, dateFin,"http://localhost:8088/prets/8/client/3", "http://localhost:8088/prets/8/livre/2");

        //when
        String res = pretService.savePret(pretForm);

        //then
       // verify(livreRepository, times(2)).getById(any(Long.class));
        assertThat(res).isEqualTo(" ");

    }


}
