package ua.yehorbilash.RESTApiSensor.RestApiSensorProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.models.Measurement;

/**
 * This repository interface provides methods for standard CRUD operations (e.g., save, delete, findAll), as well
 * as the custom query method for counting measurements with a specific isRaining value.
 */
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    /**
     * This method declaration is a custom query method provided by Spring Data JPA.
     * It returns the count of Measurement entities in the database where the isRaining
     * attribute matches the provided isRaining value. In this case, it counts the number
     * of measurements where it is raining (isRaining is true).
     */
    int countByIsRaining(boolean isRaining);

}
