package com.bibliotheque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
public class Categorie implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String label;
    @OneToMany(mappedBy = "categorie",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Livre> livres = new ArrayList<Livre>();

    public Categorie() {
    }

    public Categorie(String code, String label) {

        this.code = code;
        this.label = label;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
