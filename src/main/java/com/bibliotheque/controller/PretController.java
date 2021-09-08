package com.bibliotheque.controller;

import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Pret;
import com.bibliotheque.repository.ClientRepository;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.PretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.http.ResponseEntity.status;

@RepositoryRestController
public class PretController {

     @Autowired
     private LivreRepository livreRepository;

     @Autowired
     private ClientRepository clientRepository;

     @Autowired
     private PretRepository pretRepository;

     @RequestMapping(method=RequestMethod.DELETE, value="/prets/{id}")
     public ResponseEntity<?> deletePret(@PathVariable Long id){
          Pret pret = pretRepository.getById(id);
          Livre livre = pret.getLivre();
          livre.setNbreExemplaires(livre.getNbreExemplaires()+1);
          livreRepository.save(livre);
          pretRepository.deleteById(id);
         return  ResponseEntity.ok(" ");
     }

     @RequestMapping(method=RequestMethod.POST, value="/prets")
      public ResponseEntity<?> savePret(@RequestBody Pret pret){
          Livre livre = livreRepository.getById(pret.getLivre().getId());
          livre.setNbreExemplaires(livre.getNbreExemplaires()-1);
          livreRepository.save(livre);
          pretRepository.save(pret);
          return  ResponseEntity.ok(" ");
     }
/*
     @RequestMapping(method=RequestMethod.GET, value="/prets")
     public ResponseEntity<?> getPrets(){
          List<Pret> liste = pretRepository.findAll();
          CollectionModel<Pret> resources = CollectionModel.of(liste);
          resources.add(linkTo(methodOn(PretController.class).getPrets()).withSelfRel());
          return ResponseEntity.ok(resources);
     }*/


}
