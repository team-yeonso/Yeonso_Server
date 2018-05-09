package com.yenso.yensoserver.Service.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Configuration
@EnableAsync
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender sender;

    @Override
    @Async
    public void sendMessage(String to, String title, String body) {
        System.out.println("meesage");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(title);
            message.setText(body);
            sender.send(message);
        }
    }
