package com.bibliotheque.service;

import com.bibliotheque.controller.PretForm;

public interface PretService {
    public String deletePret(Long id);
    public String savePret(PretForm pretForm);
}
