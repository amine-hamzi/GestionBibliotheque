package com.bibliotheque.UnitTests;

import com.bibliotheque.service.Email;
import com.bibliotheque.service.EmailService;
import com.bibliotheque.service.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = { "test"})
public class EmailServiceTest {


    @InjectMocks
    private EmailServiceImpl emailService ;

    @MockBean
    private JavaMailSender mailSender ;

    @Test
    public void sendingEmail(){
       //arrange
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ambiblio01@gmail.com");
        message.setTo("hamzimohammedhamzi@gmail.com");
        message.setText("test");
        message.setSubject("obj");

        doNothing().when(mailSender).send(message);

        Email email = new Email("hamzimohammedhamzi@gmail.com","body","subject");
        //when
        Boolean res = emailService.sendSimpleEmail(email);

        //then
        assertThat(res).isEqualTo(true);

    }



}
