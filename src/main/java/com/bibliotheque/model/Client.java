package com.bibliotheque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String prenom;
    private String travail;
    private String adresse;
    private String email;
    private Date dateCreation;
    private Long retard = Long.valueOf(0);
    private Date debutRetard ;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Pret> prets = new ArrayList<Pret>();
     @OneToMany(mappedBy = "client", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
     private Collection<Reservation> reservations = new ArrayList<Reservation>();

     public Client() {
     }
     public Client( String nom, String prenom, String travail, String adresse, String email, Date dateCreation) {
         this.nom = nom;
         this.prenom = prenom;
         this.travail = travail;
         this.adresse = adresse;
         this.email = email;
         this.dateCreation = dateCreation;

     }

     @Override
     public String toString() {
         return "Client{" +
                 "id=" + id +
                 ", nom='" + nom + '\'' +
                 ", prenom='" + prenom + '\'' +
                 ", travail='" + travail + '\'' +
                 ", adresse='" + adresse + '\'' +
                 ", email='" + email + '\'' +
                 ", dateCreation=" + dateCreation +
                 '}';
     }
 }
