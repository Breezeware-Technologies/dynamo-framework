package net.breezeware.dynamo.util.mail.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import net.breezeware.dynamo.util.mail.api.EmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceJavaMailImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailContentBuilder mailContentBuilder;

    Logger logger = LoggerFactory.getLogger(EmailServiceJavaMailImpl.class);

    public void sendMail(String from, String to, String subject, Map<String, String> keyVals, String templateName) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {

            logger.info("Sending...");

            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);

            String mailContent = mailContentBuilder.build(keyVals, templateName);

            logger.info("Mail content = {}", mailContent);

            message.setText(mailContent, true);

            javaMailSender.send(mimeMessage);

            logger.info("Done!");
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}