package com.bibliotheque.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;


@Data @AllArgsConstructor @NoArgsConstructor
public class AppUser {
    private Long Id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Collection<AppRole> roles = new ArrayList<>();
}
