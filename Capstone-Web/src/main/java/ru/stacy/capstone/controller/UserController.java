package ru.stacy.capstone.controller;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.stacy.capstone.dto.UserDTO;
import ru.stacy.capstone.dto.UserResponseDTO;
import ru.stacy.capstone.model.User;
import ru.stacy.capstone.repository.EventRepository;
import ru.stacy.capstone.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static ru.stacy.capstone.util.TokenStatus.TOKEN_VALID;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
public class UserController {

    private UserService userService;

    private ModelMapper modelMapper;

    private EventRepository eventRepository;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, EventRepository eventRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
    }

    @PostMapping("/signIn")
    @ApiOperation(value = "${UserController.signin}")
    @ApiResponses(value = {//
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public String login(//
                        @ApiParam("Username") @RequestParam String username, //
                        @ApiParam("Password") @RequestParam String password) {
        return userService.signIn(username, password);
    }

    @PostMapping("/signUp")
    @ApiOperation(value = "${UserController.signup}")
    public String signup(@ApiParam("Signup User") @RequestBody UserDTO user) {
        return userService.signUp(modelMapper.map(user, User.class));
    }

    @DeleteMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.delete}")
    public String delete(@ApiParam("Username") @PathVariable String username) {
        userService.delete(username);
        return username;
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class)
    public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
        return modelMapper.map(userService.search(username), UserResponseDTO.class);
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class)
    public UserResponseDTO whoami(HttpServletRequest req) {
        return modelMapper.map(userService.getLoggedInUser(req), UserResponseDTO.class);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }

    @PostMapping("/register/{eventId}")
    public ResponseEntity registerToEvent(HttpServletRequest request, @PathVariable Long eventId) {
        if (eventRepository.findOne(eventId) != null) {
            User user = userService.registerToEvent(request, eventId);
            return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/verify")
    public ResponseEntity verifyAccount(@RequestParam String token) {
        if (userService.verify(token).equals(TOKEN_VALID)) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
