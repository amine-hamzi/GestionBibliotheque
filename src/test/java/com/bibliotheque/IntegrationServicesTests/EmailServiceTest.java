package com.bibliotheque.IntegrationServicesTests;


import com.bibliotheque.service.Email;
import com.bibliotheque.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;


    @Test
    public void sendEmailTest(){
        //arrange
        Email email = new Email("hamzimohammedhamzi@gmail.com", "body","subject");

        //when
        Boolean res = this.emailService.sendSimpleEmail(email);

        //then
        assertThat(res).isEqualTo(true);
    }

}
