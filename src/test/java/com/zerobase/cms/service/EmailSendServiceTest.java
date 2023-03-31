package com.zerobase.cms.service;

import com.zerobase.cms.client.MailgunClient;
import com.zerobase.cms.client.mailgun.SendMailForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmailSendServiceTest {

    @Autowired
    private MailgunClient mailgunClient;

    @Test
    public void EmailTest() {
        SendMailForm sendMailForm = SendMailForm.builder()
                .from("abc111@test.com")
                .to("kys1064@naver.com")
                .subject("Test_Email")
                .text("Test email.")
                .build();

        ResponseEntity<String> response = mailgunClient.sendEmail(sendMailForm);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "발송 실패");
    }
}