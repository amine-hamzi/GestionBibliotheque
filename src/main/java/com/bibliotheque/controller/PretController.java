package com.bibliotheque.controller;

import com.bibliotheque.model.Client;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.repository.ClientRepository;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.service.PretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.status;

@RepositoryRestController
public class PretController {

    @Autowired
    private PretService pretService;

     @RequestMapping(method=RequestMethod.DELETE, value="/prets/{id}")
     public ResponseEntity<?> deletePret(@PathVariable Long id) throws ParseException {
         return  ResponseEntity.ok(pretService.deletePret(id));
     }

     @RequestMapping(method=RequestMethod.POST, value="/prets")
      public ResponseEntity<?> savePret(@RequestBody PretForm pretForm) throws ParseException {
         return  ResponseEntity.ok(pretService.savePret(pretForm));

     }
}
