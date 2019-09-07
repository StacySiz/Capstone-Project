package ru.stacy.capstone.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.stacy.capstone.model.Place;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlaceRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    public void whenFindByName_thenReturnPlace() {
        //given
        Place place = new Place();
        place.setName("TestUsername");
        entityManager.persist(place);
        entityManager.flush();

        //when
        Place foundPlace = placeRepository.findByName(place.getName());

        //then
        assertThat(foundPlace.getName())
                .isEqualTo(place.getName());
    }
}
