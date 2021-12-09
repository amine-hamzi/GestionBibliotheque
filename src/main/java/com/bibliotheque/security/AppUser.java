package com.bibliotheque.security;

import lombok.Data;


import java.util.ArrayList;
import java.util.Collection;


@Data
public class AppUser {
    private Long Id;
    private String username;
    private String password;
    private Collection<AppRole> roles = new ArrayList<>();
}
