package com.bibliotheque.service;

import com.bibliotheque.controller.PretForm;
import com.bibliotheque.model.Client;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.repository.ClientRepository;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class PretServiceImpl implements PretService{

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public PretServiceImpl() {

    }

    @Override
    public String deletePret(Long id) {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Pret pret = pretRepository.getById(id);
        Livre livre = pret.getLivre();
        livre.setNbreExemplaires(livre.getNbreExemplaires()+1);

        String currentDate = simpleDateFormat1.format(new Date());
        String dateFin = simpleDateFormat1.format(pret.getDateFin());
        LocalDate d1 = LocalDate.parse(currentDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate d2 = LocalDate.parse(dateFin, DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay());
        if(!diff.isNegative()){
            long diffDays = diff.toDays();
            if(diffDays != 0){
                Client client = pret.getClient();
                client.setRetard(diffDays + client.getRetard());
                client.setDebutRetard(new Date());
                clientRepository.save(client);
            }
        }
        livreRepository.save(livre);

        List<Reservation> liste = reservationRepository.findByLivre(livre);
        pretRepository.deleteById(id);
        Reservation reservation = null;
        if(liste.size() != 0)
            for(int i =0; i<liste.size();i++){
                String add = liste.get(i).getClient().getEmail();
                Email email = new Email(add,"livre disponible","réservation");
                this.emailService.sendSimpleEmail(email);
            }
            return  " ";
    }

    @Override
    public String savePret(PretForm pretForm) {
        System.out.println("saving pret *********************");
        Pret pret = mapPretFormToPret(pretForm);
        Client client = pret.getClient();
        if(client.getRetard() != 0){
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = simpleDateFormat1.format(new Date());
            String debutRetard = simpleDateFormat1.format(client.getDebutRetard());
            LocalDate d1 = LocalDate.parse(currentDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d2 = LocalDate.parse(debutRetard, DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff = Duration.between(d2.atStartOfDay(), d1.atStartOfDay()).abs();
            long diffDays = diff.toDays();
            if(diffDays >= client.getRetard()){
                Livre livre = livreRepository.getById(pret.getLivre().getId());
                livre.setNbreExemplaires(livre.getNbreExemplaires()-1);
                client.setDebutRetard(null);
                client.setRetard(Long.valueOf(0));
                clientRepository.save(client);
                livreRepository.save(livre);
                pretRepository.save(pret);
                return  " ";
            }
            else{
                return  "interdit"+client.getRetard();
            }
        }else{
            Livre livre = livreRepository.getById(pret.getLivre().getId());
            livre.setNbreExemplaires(livre.getNbreExemplaires()-1);
            livreRepository.save(livre);
            pretRepository.save(pret);
            return  " ";
        }
    }

    private Pret mapPretFormToPret(PretForm pretForm){
        Pret pret = new Pret();
        pret.setDateFin(pretForm.getDateFin());
        pret.setDateDebut(pretForm.getDateDebut());

        String urlClient = pretForm.getClient();
        int index1 = urlClient.lastIndexOf("/");
        int id = Integer.parseInt(urlClient.substring(index1+1));
        pret.setClient(clientRepository.getById(Long.valueOf(id)));

        String urlLivre = pretForm.getLivre();
        index1 = urlLivre.lastIndexOf("/");
        id= Integer.parseInt(urlLivre.substring(index1+1));
        pret.setLivre(livreRepository.getById(Long.valueOf(id)));
        return pret;
    }
}
