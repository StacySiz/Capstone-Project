package ru.stacy.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stacy.capstone.model.User;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);

}
