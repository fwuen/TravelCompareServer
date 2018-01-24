package travelcompare.restapi.data.service;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travelcompare.restapi.api.model.request.Validation;
import travelcompare.restapi.data.model.RouteInfo;
import travelcompare.restapi.data.repository.RouteInfoRepository;
import travelcompare.restapi.data.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RouteInfoService {

    @Autowired
    private RouteInfoRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RouteInfo> getRouteInfoById(long id) {
        Preconditions.checkArgument(id > 0);

        return repository.findFirstByIdEquals(id);
    }

    public List<RouteInfo> getRouteInfosByCreatorId(long creatorId) {
        Preconditions.checkArgument(creatorId > 0);

        return repository.findAllByCreatorId(creatorId);
    }

    public RouteInfo createRoute(@NonNull RouteInfo routeInfoData) {
        Validation validation = routeInfoData.valid();

        Preconditions.checkArgument(validation.isValid(), validation.getMessage());

        RouteInfo routeInfo = new RouteInfo();
        routeInfo.setLat_start(routeInfoData.getLat_start());
        routeInfo.setLat_end(routeInfoData.getLat_end());
        routeInfo.setLon_start(routeInfoData.getLon_start());
        routeInfo.setLon_end(routeInfoData.getLon_end());
        routeInfo.setDistance(routeInfoData.getDistance());
        routeInfo.setPrice(routeInfoData.getPrice());
        routeInfo.setCreatorId(routeInfoData.getCreatorId());

        return repository.save(routeInfo);
    }

    public RouteInfo updateRoute(@NonNull RouteInfo routeInfo) {
        Preconditions.checkArgument(userRepository.existsByIdEquals(routeInfo.getCreatorId()));

        return repository.save(routeInfo);
    }

    public void deleteRouteInfoById(long id) {
        Preconditions.checkArgument(id > 0);

        repository.removeAllById(id);
    }

}
