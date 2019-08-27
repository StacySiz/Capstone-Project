package ru.stacy.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stacy.capstone.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
