package ru.stacysiz.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.stacysiz.models.InvitationDTO;
import ru.stacysiz.services.EmailService;

@Controller
public class NotificationController {

    Logger logger = LoggerFactory.getLogger(NotificationController.class);


    private EmailService emailService;

    @Autowired
    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/notification/email")
    public Object sendNotification(@RequestBody InvitationDTO invitation) {
        logger.info("Notification");
        emailService.sendEmail(invitation);

        return ResponseEntity.ok().build();
    }
}
