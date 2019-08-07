package ru.stacy.capstone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.stacy.capstone.service.UserService;

@SpringBootApplication
public class CapstoneApp {

  @Autowired
  private UserService userService;

  public static void main(String[] args) {
    SpringApplication.run(CapstoneApp.class, args);
  }


}
