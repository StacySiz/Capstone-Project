package ru.stacysiz.utill;

import org.springframework.stereotype.Component;
import ru.stacysiz.models.InvitationDTO;

@Component
public class MessageTemplateHelper {

    public String getInvitationMessageText(InvitationDTO inviteInfo){
        String invite = "Hello, "  + System.lineSeparator() +
                " You have been invited to " + inviteInfo.getName() +
                "What's it all about?" + inviteInfo.getDescription() + System.lineSeparator();

        String paymentDetails;
        if (inviteInfo.isFree()){
            paymentDetails = getEventFreeMessage();
        }else paymentDetails = getEventPaidMessage(inviteInfo.getPrice());

        String invitationMessage = invite.concat(paymentDetails);

        return invitationMessage;
    }

    public String getVerifyAccountMeassge(String verifyLink){
        String verifyMessage = "Welcome to local stand up club!" + System.lineSeparator() +
                "To activate your account click this link! " + verifyLink;

        return verifyMessage;
    }

    private String getEventFreeMessage(){
        return "AND THAT'S ALL FOR FREE!" + System.lineSeparator() + "COME AND HAVE FUN!";
    }

    private String getEventPaidMessage(Integer price){
        return "And that's is only for " + price;
    }
}
