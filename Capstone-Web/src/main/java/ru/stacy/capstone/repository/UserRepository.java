package ru.stacy.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stacy.capstone.model.User;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);

}
