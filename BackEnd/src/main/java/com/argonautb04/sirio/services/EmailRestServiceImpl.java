package com.argonautb04.sirio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailRestServiceImpl implements EmailRestService {

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public Boolean sendEmail(String receiver, String subject, String content) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(content);
        emailSender.send(message);
        return true;
    }
}
