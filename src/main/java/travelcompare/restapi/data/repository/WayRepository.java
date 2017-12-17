package travelcompare.restapi.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import travelcompare.restapi.data.model.Way;

import java.util.Optional;

@Repository
public interface WayRepository extends CrudRepository<Way, Long> {
    Optional<Way> findFirstByIdEquals(long id);
}
