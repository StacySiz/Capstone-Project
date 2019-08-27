package ru.stacy.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.stacy.capstone.exception.CustomException;
import ru.stacy.capstone.model.Event;
import ru.stacy.capstone.model.Role;
import ru.stacy.capstone.model.User;
import ru.stacy.capstone.model.VerificationToken;
import ru.stacy.capstone.repository.EventRepository;
import ru.stacy.capstone.repository.UserRepository;
import ru.stacy.capstone.repository.VerificationTokenRepository;
import ru.stacy.capstone.security.JwtTokenProvider;
import ru.stacy.capstone.util.TokenStatus;
import ru.stacy.capstone.util.VerificationHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static ru.stacy.capstone.util.TokenStatus.TOKEN_VALID;

@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    private EventRepository eventRepository;

    private NotificationService notificationService;

    private VerificationHelper verificationHelper;

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                       EventRepository eventRepository, NotificationService notificationService, VerificationHelper verificationHelper, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.eventRepository = eventRepository;
        this.notificationService = notificationService;
        this.verificationHelper = verificationHelper;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public String signIn(String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            if (user != null && user.isEnabled()) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                return jwtTokenProvider.createToken(username, Role.ROLE_CLIENT);
            }
            throw new CustomException("Account is not verified", HttpStatus.UNAUTHORIZED);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signUp(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(false);
            userRepository.save(user);
            VerificationToken verificationToken = verificationHelper.createVerificationToken(user);
            notificationService.verifyRegistration(user.getId(), verificationToken.getToken());
            return jwtTokenProvider.createToken(user.getUsername(), Role.ROLE_CLIENT);
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public TokenStatus verify(String token) {
        TokenStatus verificationResult = verificationHelper.validateVerificationToken(token);
        if (verificationResult.equals(TOKEN_VALID)) {
            VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
            User user = verificationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
        }
        return verificationResult;
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    public User search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public User getLoggedInUser(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, Role.ROLE_CLIENT);
    }


    //TODO move it event service
    public User registerToEvent(HttpServletRequest request, Long eventId) {
        User user = getLoggedInUser(request);
        Event event = eventRepository.findOne(eventId);
        Set<User> participants = event.getParticipants();
        participants.add(user);
        eventRepository.save(event);
//        Set<Event> activeEvents = user.getActiveEvents();
//        activeEvents.add(event);
//        user.setActiveEvents(activeEvents);
        userRepository.save(user);
        return user;
    }
}
