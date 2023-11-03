package ua.yehorbilash.RESTApiSensor.RestApiSensorProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.dto.SensorDTO;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.service.SensorService;

/**
 * The SensorController class is a Spring MVC controller in
 * a Java Spring Boot application responsible for handling HTTP requests related to sensors.
 */
@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * @PostMapping("/registration"): This annotation specifies that this controller
     * method is responsible for handling HTTP POST requests to the "/registration" endpoint.
     * This method is responsible for sensor registration. It takes a SensorDTO object as the request body,
     * which represents the data of the sensor to be registered. The @Validated annotation is used to trigger
     * validation of the sensorDTO object based on the validation rules defined in the associated validation annotations within the SensorDTO class.
     * The method returns a ResponseEntity containing a String.
     * if (sensorService.sensorExist(sensorDTO.getName())): This line checks whether a sensor with the same name as the one provided
     * in the sensorDTO already exists. It does this by calling the sensorExist method from the sensorService.
     * If a sensor with the same name is found, it returns a response with an HTTP status of 409 Conflict and a message
     * indicating that a sensor with the provided name already exists.
     * <p>
     * sensorService.saveNewSensor(sensorDTO): If there is no sensor with the same name, this line calls
     * the saveNewSensor method from the sensorService to save the new sensor information into the system.
     * <p>
     * return ResponseEntity.status(HttpStatus.CREATED).body("Sensor registered successfully"): If the sensor
     * is successfully saved, it returns a response with
     * an HTTP status of 201 Created and a message indicating that the sensor was registered successfully.
     */
    @PostMapping("/registration")
    public ResponseEntity<String> sensorRegistration(@Validated @RequestBody SensorDTO sensorDTO) {

        if (sensorService.sensorExist(sensorDTO.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sensor with this name already exists");
        }
        sensorService.saveNewSensor(sensorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sensor registered successfully");
    }

}
