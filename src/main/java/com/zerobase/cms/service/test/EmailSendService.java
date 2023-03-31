package com.zerobase.cms.service.test;

import com.zerobase.cms.client.MailgunClient;
import com.zerobase.cms.client.mailgun.SendMailForm;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final MailgunClient mailgunClient;

    public String sendEmail() {

        SendMailForm form = SendMailForm.builder()
                .from("test@test.com")
                .to("kys1064@naver.com")
                .subject("Test email from me")
                .text("Test Text")
                .build();

        return mailgunClient.sendEmail(form).getBody();
    }
}
