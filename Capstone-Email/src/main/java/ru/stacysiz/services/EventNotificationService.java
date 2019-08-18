package ru.stacysiz.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.stacysiz.models.InvitationDTO;
import ru.stacysiz.utill.MessageTemplateHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EventNotificationService implements EmailService {

    private String EVENT_INVITATION = "stand up event";

    private JavaMailSender javaMailSender;
    private MessageTemplateHelper templateHelper;

    Logger logger = LoggerFactory.getLogger(EventNotificationService.class);


    @Autowired
    public EventNotificationService(JavaMailSender javaMailSender, MessageTemplateHelper templateHelper) {
        this.javaMailSender = javaMailSender;
        this.templateHelper = templateHelper;
    }

    @Override
    public void sendEmail(InvitationDTO invitationDTO) {
        MimeMessage message = javaMailSender.createMimeMessage();
        List<String> guests = invitationDTO.getGuests();
        for (String guest : guests){
            try {
                message.setContent(templateHelper.getInvitationMessageText(invitationDTO), "text/html");
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
                messageHelper.setTo(guest);
                messageHelper.setSubject(EVENT_INVITATION);
                logger.info("Email to " + guest + " was sent");
//                messageHelper.setText(text, true);

            } catch (MessagingException e) {
                throw new IllegalArgumentException(e);
            }
        }
        javaMailSender.send(message);
    }
}
