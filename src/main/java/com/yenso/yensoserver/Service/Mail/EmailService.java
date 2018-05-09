package com.yenso.yensoserver.Service.Mail;

import javax.mail.SendFailedException;

public interface EmailService {
    void sendMessage(String to, String title, String body) throws SendFailedException;
}
