package com.bibliotheque.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data @AllArgsConstructor @NoArgsConstructor
public class AppRole {

    private Long id;
    private String role;
}
