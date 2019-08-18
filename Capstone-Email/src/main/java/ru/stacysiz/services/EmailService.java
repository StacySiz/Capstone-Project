package ru.stacysiz.services;

import ru.stacysiz.models.InvitationDTO;

import java.util.List;

public interface EmailService{
    void sendEmail(InvitationDTO invitationDTO);
}
