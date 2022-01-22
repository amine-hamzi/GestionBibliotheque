package com.bibliotheque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Data
@Entity
public class Livre implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String isbn;
    private String title;
    private Date dateCreation;
    private int nbreExemplaires;
    private String auteur;
    @OneToMany(mappedBy = "livre",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Pret> prets = new ArrayList<Pret>();
    @ManyToOne(optional = false)
    private Categorie categorie;
    @OneToMany(mappedBy = "livre", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Reservation> reservations = new ArrayList<Reservation>();

    public Livre() {
    }

    public Livre( String isbn, String title, Date dateCreation, int nbreExemplaires, String auteur, Categorie categorie) {

        this.isbn = isbn;
        this.title = title;
        this.dateCreation = dateCreation;
        this.nbreExemplaires = nbreExemplaires;
        this.auteur = auteur;
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", dateCreation=" + dateCreation +
                ", nbreExemplaires=" + nbreExemplaires +
                ", auteur='" + auteur + '\'' +
                ", categorie=" + categorie +
                '}';
    }
}
