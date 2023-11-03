package ua.yehorbilash.RESTApiSensor.RestApiSensorProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.dto.SensorDTO;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.models.Sensor;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.repository.SensorRepository;

/**
 * The SensorService class is responsible for sensor-related operations.
 * It provides methods to check if a sensor with a given name already exists
 * in the database and to save a new sensor to the database.
 */
@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    /**
     * This method is created to check if there is already such a sensor in the database
     *
     * @param sensorName sensor name
     * @return true of false based on whether such a sensor has already been registered
     */
    public boolean sensorExist(String sensorName) {
        return sensorRepository.existsByName(sensorName);
    }

    /**
     * This method save a new sensor to database
     *
     * @param sensorDTO get sensor name from user
     */
    @Transactional
    public void saveNewSensor(SensorDTO sensorDTO) {
        Sensor sensor = new Sensor();
        sensor.setName(sensorDTO.getName());

        sensorRepository.save(sensor);
    }
}

