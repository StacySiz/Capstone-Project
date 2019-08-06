package ru.stacy.capstone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.stacy.capstone.model.Role;
import ru.stacy.capstone.model.User;
import ru.stacy.capstone.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class CapstoneApp implements CommandLineRunner {

  @Autowired
  private UserService userService;

  public static void main(String[] args) {
    SpringApplication.run(CapstoneApp.class, args);
  }


  @Override
  public void run(String... params) throws Exception {
    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword("admin");
    admin.setEmail("admin@email.com");
    admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

    userService.signUp(admin);

    User client = new User();
    client.setUsername("client");
    client.setPassword("client");
    client.setEmail("client@email.com");
    client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

    userService.signUp(client);
  }

}
