package ua.yehorbilash.RESTApiSensor.RestApiSensorProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.dto.MeasurementDTO;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.models.Measurement;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.models.Sensor;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.repository.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * MeasurementService class acts as an intermediary between the controller and the database,
 * providing methods for retrieving all measurements, saving new measurements from MeasurementDTO objects,
 * and counting the number of rainy days in the database. It leverages a Spring Data JPA repository for
 * data access and includes transaction management for the save method to ensure consistency when storing measurements.
 */
@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;


    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    /**
     * @return all measurement from database
     */
    public List<Measurement> showAllMeasurement() {
        return measurementRepository.findAll();
    }

    /**
     * This save method creates a new Measurement entity, associates it with a Sensor,
     * and saves it to the database with the provided measurement data. It leverages
     * the @Transactional annotation to ensure that the database changes are consistent and atomic.
     * This method is typically used to add new measurements to the database, such as when receiving
     * data from sensors or other data sources.
     *
     * @param measurementDTO it sets the name of the Sensor entity based on the sensorName property from the measurementDTO.
     */
    @Transactional
    public void save(MeasurementDTO measurementDTO) {
        Measurement measurement = new Measurement();
        measurement.setRaining(measurementDTO.isRaining());
        measurement.setTemperature((float) measurementDTO.getValue());

        Sensor sensor = new Sensor();
        sensor.setName(measurementDTO.getSensorName());
        sensor.setMeasurements(Collections.singletonList(measurement));
        measurement.setSensor(sensor);
        measurement.setTime(LocalDateTime.now());
        measurementRepository.save(measurement);
    }

    /**
     * @return count of rainy days
     */
    public int getRainyDays() {
        return measurementRepository.countByIsRaining(true);
    }

}
