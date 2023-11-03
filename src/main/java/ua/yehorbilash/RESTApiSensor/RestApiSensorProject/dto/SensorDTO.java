package ua.yehorbilash.RESTApiSensor.RestApiSensorProject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * SensorDTO class is used to encapsulate data related to a sensor.
 * It enforces validation constraints on the sensor's name, ensuring that
 * it is not null and that its length falls within the specified range.
 */
public class SensorDTO {
    @NotNull(message = "Sensor name is required")
    @Size(min = 3, max = 30, message = "Sensor name length should be between 3 or 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
