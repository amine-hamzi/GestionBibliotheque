package com.bibliotheque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Pret implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Date dateDebut;
    private Date dateFin;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Client client;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Livre livre;

    public Pret( Date dateDebut, Date dateFin, Client client, Livre livre) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.client = client;
        this.livre = livre;
    }

    public Pret() {
    }
}
