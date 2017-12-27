package travelcompare.restapi.data.service;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travelcompare.restapi.api.model.request.Validation;
import travelcompare.restapi.api.model.request.WayData;
import travelcompare.restapi.data.model.Way;
import travelcompare.restapi.data.repository.UserRepository;
import travelcompare.restapi.data.repository.WayRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WayService {

    @Autowired
    private WayRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Way> getWayById(long id) {
        Preconditions.checkArgument(id > 0);

        return repository.findFirstByIdEquals(id);
    }

    public List<Optional<Way>> getWayByUsername(String name) {
        Preconditions.checkNotNull(name);
        Preconditions.checkArgument(name.length() > 0);

        return repository.findAllByUsername(name);
    }

    public Way createRoute(@NonNull WayData wayData) {
        Validation validation = wayData.valid();

        Preconditions.checkArgument(validation.isValid(), validation.getMessage());

        Way way = new Way();
        way.setLat_start(wayData.getLat_start());
        way.setLat_end(wayData.getLat_end());
        way.setLon_start(wayData.getLon_start());
        way.setLon_end(wayData.getLon_end());
        way.setDistance(wayData.getDistance());
        way.setPrice(wayData.getPrice());
        way.setCreatorId(wayData.getCreatorId());

        return repository.save(way);
    }

    public Way updateRoute(@NonNull Way way) {
        Preconditions.checkArgument(userRepository.existsByIdEquals(way.getCreatorId()));

        return repository.save(way);
    }

    public void deleteWayById(long id) {
        Preconditions.checkArgument(id > 0);

        repository.removeAllById(id);
    }

}
