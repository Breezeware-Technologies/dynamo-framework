package net.breezeware.dynamo.util.mail.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import net.breezeware.dynamo.util.mail.api.EmailService;

@Component
public class EmailServiceJavaMailImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailContentBuilder mailContentBuilder;

    Logger logger = LoggerFactory.getLogger(EmailServiceJavaMailImpl.class);

    /**
     * Send an email to the a recipient.
     * @param from         the sender's email address
     * @param to           the recipient's email address
     * @param subject      the subject in the email
     * @param keyVals      the map of key, value pairs that will be inserted into
     *                     the email body
     * @param templateName the template used to compose the email body
     */
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