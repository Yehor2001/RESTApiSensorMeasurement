package ua.yehorbilash.RESTApiSensor.RestApiSensorProject.dto;

import jakarta.validation.constraints.*;
import org.springframework.lang.NonNull;

/**
 * The MeasurementDTO class is used for encapsulating data related to measurements,
 * including the numeric value of the measurement, whether it's associated with a trigger event,
 * the sensor name, and whether it is currently raining. The class uses annotations
 * like @NotNull, @DecimalMin, and @DecimalMax to enforce validation constraints on its fields to ensure
 * that the data conforms to specified rules or limits. This class is typically used for transferring measurement
 * data between different components of the application, such as between a client and a server in a RESTful API
 * or as part of a data processing pipeline.
 */
public class MeasurementDTO {
    @NonNull
    @DecimalMin(value = "-100")
    @DecimalMax(value = "100")
    private double value;

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    private boolean trigger;
    @NotNull
    private String sensorName;

    @NotNull
    private boolean raining;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }
}
