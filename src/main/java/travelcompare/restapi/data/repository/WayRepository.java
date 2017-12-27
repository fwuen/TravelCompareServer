package travelcompare.restapi.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import travelcompare.restapi.data.model.Way;

import java.util.List;
import java.util.Optional;

@Repository
public interface WayRepository extends CrudRepository<Way, Long> {
    Optional<Way> findFirstByIdEquals(long id);
    //TODO remove geht so noch nicht wahrscheinlich
    void removeAllById(long id);
    List<Optional<Way>> findAllByUsername(String username);
}
