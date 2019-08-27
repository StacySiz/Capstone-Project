package ru.stacysiz.services;

import ru.stacysiz.models.InvitationDTO;

import java.util.List;
import java.util.Map;

public interface EmailService{
    void sendEmail(InvitationDTO invitationDTO);

    void sendVerifyEmail(Map<String,String> mailAndToken);
}
