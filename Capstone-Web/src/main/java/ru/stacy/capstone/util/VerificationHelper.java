package ru.stacy.capstone.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stacy.capstone.model.User;
import ru.stacy.capstone.model.VerificationToken;
import ru.stacy.capstone.repository.VerificationTokenRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static ru.stacy.capstone.util.TokenStatus.*;

@Component
public class VerificationHelper {

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationHelper(VerificationTokenRepository tokenRepository) {
        this.verificationTokenRepository = tokenRepository;
    }

    private static final int EXPIRATION = 60 * 24;

    public VerificationToken createVerificationToken(User user) {
        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(calculateExpiryDate());
        token.setUser(user);
        verificationTokenRepository.save(token);
        return token;
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, VerificationHelper.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    public TokenStatus validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }
        long time = Calendar.getInstance().getTime().getTime();
        Date expiryDate = verificationToken.getExpiryDate();
        if (expiryDate.getTime() - time <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        return TOKEN_VALID;
    }
}
