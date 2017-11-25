package travelcompare.restapi.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import travelcompare.restapi.data.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findFirstByEmailEqualsIgnoreCase(String email);

}
