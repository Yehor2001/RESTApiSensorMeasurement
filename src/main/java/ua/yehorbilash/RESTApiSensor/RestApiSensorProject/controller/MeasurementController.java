package ua.yehorbilash.RESTApiSensor.RestApiSensorProject.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.dto.MeasurementDTO;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.dto.SensorDTO;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.models.Measurement;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.service.MeasurementService;
import ua.yehorbilash.RESTApiSensor.RestApiSensorProject.service.SensorService;

import java.util.List;

/**
 * The MeasurementController class appears to be a Spring MVC controller
 * in a Java Spring Boot application responsible for handling HTTP requests
 * related to measurements and sensors
 */
@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private static final int COUNT_OF_DATA = 1000;
    private static final Logger logger = LoggerFactory.getLogger(MeasurementController.class);
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final RestTemplate restTemplate;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorService sensorService, RestTemplate restTemplate) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.restTemplate = restTemplate;
    }

    /**
     * addMeasurementData Method: This method is annotated with @PostMapping("/add"),
     * indicating that it handles HTTP POST requests to the /add endpoint. It performs the following actions:
     * 1)Registers a new sensor with the name "Sensor1000" using the SensorService.
     * 2)Saves the received measurement data (DTO) using the MeasurementService.
     * If the trigger flag in the measurementDTO is true, it sends 1000 additional measurement
     * data entries with random values and "rain" flags using the sentMeasurementUsingRestTemplate method.
     * Returns an ok response with a success message.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addMeasurementData(@RequestBody @Valid MeasurementDTO measurementDTO) {
        //Register new sensor
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("Sensor1000");
        sensorService.saveNewSensor(sensorDTO);
//        Save all data to database
        measurementService.save(measurementDTO);
        if (measurementDTO.isTrigger()) {
            for (int i = 0; i < COUNT_OF_DATA; i++) {
                MeasurementDTO measurementDTO1 = new MeasurementDTO();

                measurementDTO1.setRaining(Math.random() < 0.5);
                measurementDTO1.setValue(Math.random() * 100);
                measurementDTO1.setSensorName(sensorDTO.getName());

                try {
                    sentMeasurementUsingRestTemplate(measurementDTO1);
                } catch (Exception e) {
                    logger.error("Failed to send: " + e.getMessage());
                }

            }
        }

        return ResponseEntity.ok("Measurement data was added successful");
    }

    /**
     * This method sends a POST request to the /measurements/add endpoint on the same server
     * or a remote server (configured with BASE_URL). It uses RestTemplate to send the MeasurementDTO data as JSON.
     * It logs success or failure messages based on the response received from the server.
     */
    private void sentMeasurementUsingRestTemplate(MeasurementDTO measurementDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MeasurementDTO> requestEntity = new HttpEntity<>(measurementDTO, httpHeaders);

        String BASE_URL = "http://localhost:8080";
        ResponseEntity<String> measurementResponse = restTemplate.postForEntity(BASE_URL + "/measurements/add",
                requestEntity, String.class);

        if (measurementResponse.getStatusCode().is2xxSuccessful()) {
            logger.info("Successful: " + measurementResponse.getBody());
        } else {
            logger.error("Failed: " + measurementResponse.getBody());
            throw new RuntimeException("Failed to send");
        }
    }

    /**
     * This method is annotated with @GetMapping() and handles HTTP GET requests to the root path.
     * It uses the MeasurementService to retrieve a list of all measurements and returns them as a response.
     *
     * @return all measurement data from database
     */
    @GetMapping()
    public List<Measurement> getMeasurement() {
        return measurementService.showAllMeasurement();
    }

    /**
     * This method is annotated with @GetMapping("/rainyDaysCount") and handles HTTP GET requests to the /rainyDaysCount endpoint.
     * It uses the MeasurementService to calculate the count of rainy days and returns this count as a response.
     *
     * @return count of rainy days from database
     */
    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Integer> getRainyDaysCount() {
        int counterRainyDays = measurementService.getRainyDays();
        return ResponseEntity.ok(counterRainyDays);
    }

}
