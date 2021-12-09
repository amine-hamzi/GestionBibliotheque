package com.bibliotheque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Livre livre;

}
