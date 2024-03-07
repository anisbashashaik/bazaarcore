package com.gnt.oms.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailUtils {
    
    @Autowired
    private JavaMailSender eMailSender;
    public void sendSimpleMessage(String to, String subject, String body, java.util.List<String> emailCCList){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("anisbashashaik9966@gmail.com");
        message.setSubject(subject);
        message.setText(body);
        message.setTo(to);
        if(emailCCList != null && emailCCList.size() > 0){
            message.setCc(getCcArray(emailCCList));
        }        
        eMailSender.send(message);
    }

    private String[] getCcArray(List<String> emailCCList){
        String[] result = new String[emailCCList.size()];
        for(int i = 0; i < result.length; i++){
            result[i] = emailCCList.get(i);
        }
        return result;
    }

    public void forgotPassword(String to, String subject, String password) throws MessagingException {
        MimeMessage mimeMessage = eMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("anisbashashaik9966@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String strHtml = "Click here to login: http://localhost:4200/login";
        mimeMessage.setContent(strHtml , "text/html");
        eMailSender.send(mimeMessage);



    }
}
