package com.bibliotheque.controller;

import com.bibliotheque.model.Client;
import com.bibliotheque.model.Livre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class PretForm {
    private Date dateDebut;
    private Date dateFin;
    private String Client;
    private String Livre;
}
