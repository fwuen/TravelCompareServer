package travelcompare.restapi.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import travelcompare.restapi.data.model.RouteInfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteInfoRepository extends CrudRepository<RouteInfo, Long> {
    Optional<RouteInfo> findFirstByIdEquals(long id);
    //TODO remove geht so noch nicht wahrscheinlich
    void removeAllById(long id);
    List<Optional<RouteInfo>> findAllByCreatorId(long creatorId);
}
