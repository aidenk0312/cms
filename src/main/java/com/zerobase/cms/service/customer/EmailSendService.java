package com.zerobase.cms.service.customer;

import com.zerobase.cms.client.MailgunClient;
import com.zerobase.cms.client.mailgun.SendMailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {

    @Autowired
    private MailgunClient mailgunClient;

    @Value("${mailgun.api.key}")
    private String mailgunApuKey;

    public String sendEmail() {
        SendMailForm sendMailForm = SendMailForm.builder()
                .from("abc111@test.com")
                .to("kys1064@naver.com")
                .subject("Test_Email")
                .text("Test email.")
                .build();

        ResponseEntity<String> response = mailgunClient.sendEmail(sendMailForm);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "발송 성공";
        } else {
            return "메일 발송 실패";
        }
    }
}





