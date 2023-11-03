package ua.yehorbilash.RESTApiSensor.RestApiSensorProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.models.Sensor;

/**
 * The SensorRepository interface is
 * a Spring Data JPA repository that provides data access operations for the Sensor entity.
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    /**
     * This method declaration is a custom query method provided by Spring Data JPA.
     * It checks whether a sensor with the given name (sensorName) exists in the database.
     * It returns true if a sensor with the provided name exists and false if it does not.
     */
    boolean existsByName(String sensorName);
}
