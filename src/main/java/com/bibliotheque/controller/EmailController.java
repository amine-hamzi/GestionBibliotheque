package com.bibliotheque.controller;

import com.bibliotheque.service.Email;
import com.bibliotheque.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public void createEmploye(@RequestBody Email e) {
         emailService.sendSimpleEmail(e);
    }
}
