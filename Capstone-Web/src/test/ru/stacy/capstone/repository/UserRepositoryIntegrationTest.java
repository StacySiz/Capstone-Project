package ru.stacy.capstone.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.stacy.capstone.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnUser() {
        //given
        User user = new User();
        user.setUsername("TestUsername");
        user.setEmail("testEmail");
        entityManager.persist(user);
        entityManager.flush();

        //when
        User foundUser = userRepository.findByUsername(user.getUsername());

        //then
        assertThat(foundUser.getUsername())
                .isEqualTo(user.getUsername());
    }
}
