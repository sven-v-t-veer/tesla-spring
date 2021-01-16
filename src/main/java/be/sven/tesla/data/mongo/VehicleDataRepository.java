package be.sven.tesla.data.mongo;

import be.sven.tesla.core.VehicleData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface VehicleDataRepository extends MongoRepository<VehicleData, UUID> {

    List<VehicleData> findAllById(Long ig);

}
