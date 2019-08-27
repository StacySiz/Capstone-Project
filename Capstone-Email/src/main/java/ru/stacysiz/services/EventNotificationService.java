package ru.stacysiz.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.stacysiz.models.InvitationDTO;
import ru.stacysiz.utill.MessageTemplateHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class EventNotificationService implements EmailService {

    private String EVENT_INVITATION = "stand up event";
    private String ACCOUNT_VIRIFICATION = "verification";

    @Value("${web.service.verify}")
    private String serviceURL;

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
                message.setText(templateHelper.getInvitationMessageText(invitationDTO));
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, false);
                messageHelper.setTo(guest);
                messageHelper.setSubject(EVENT_INVITATION);
                javaMailSender.send(message);
                logger.info("Email to " + guest + " was sent");
            } catch (MessagingException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void sendVerifyEmail(Map<String, String> mailAndToken) {
        Map.Entry<String, String> entry = mailAndToken.entrySet().iterator().next();
        String mail = entry.getKey();
        String token = entry.getValue();

        String verifyLink = serviceURL.concat("?token=").concat(token);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, false);
            message.setText(templateHelper.getVerifyAccountMeassge(verifyLink));
            messageHelper.setTo(mail);
            messageHelper.setSubject(ACCOUNT_VIRIFICATION);
            javaMailSender.send(message);
            logger.info("Verification was sent to " + mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
