package travelcompare.restapi.data.repository;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import travelcompare.restapi.data.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findFirstByEmailEqualsIgnoreCase(@NonNull String email);
    Optional<User> findFirstByIdEquals(long id);
    boolean existsByEmailEqualsIgnoreCase(@NonNull String email);
    boolean existsByIdEquals(long id);
    void deleteByEmailIgnoreCase(@NonNull String email);
}
