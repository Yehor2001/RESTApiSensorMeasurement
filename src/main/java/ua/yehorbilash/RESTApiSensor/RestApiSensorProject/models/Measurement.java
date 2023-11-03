package ua.yehorbilash.RESTApiSensor.RestApiSensorProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

/**
 * Measurement class is a representation of a measurement record in a relational database.
 * It includes fields for the measurement's temperature, rain status, and the associated sensor.
 * This class is typically used in the context of a Java application that interacts with a database,
 * and it leverages JPA and Hibernate for database operations. The annotations help define
 * the database mapping and validation rules for the fields.
 */

@Entity
@Table(name = "Measurement")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @DecimalMin(value = "-100")
    @DecimalMax(value = "100")
    @Column(name = "temperature")
    private float temperature;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @NotNull
    @Column(name = "is_raining")
    private boolean isRaining;
    @Column(name = "time")
    private LocalDateTime time;

    public Measurement() {
    }

    public Measurement(float temperature, Sensor sensor, boolean isRaining) {
        this.temperature = temperature;
        this.sensor = sensor;
        this.isRaining = isRaining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
