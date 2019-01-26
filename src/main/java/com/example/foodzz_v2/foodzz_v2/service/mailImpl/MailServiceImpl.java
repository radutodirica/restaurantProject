package com.example.foodzz_v2.foodzz_v2.service.mailImpl;

import com.example.foodzz_v2.foodzz_v2.service.mail.MailService;
import com.example.foodzz_v2.foodzz_v2.util.mail.MailTextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendVerificationMail(String email, String username, String veririficationLink) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(email);
            helper.setText(MailTextUtil.getVerificationMailBody(username, veririficationLink));
            helper.setSubject("Mail From My Application");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);

    }

    @Override
    public void sendConfirmationMail(String email, String username) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try{
            helper.setTo(email);
            helper.setText(MailTextUtil.getConfirmationMailBody(username));
            helper.setSubject(MailTextUtil.getConfirmationMailSubject(username));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }

    @Override
    public void sendForgotPasswordMail(String email) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try{
            helper.setTo(email);
            helper.setText(MailTextUtil.getForgotPasswordMailBody());
            helper.setSubject(MailTextUtil.getForgotPasswordMailSubject());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
