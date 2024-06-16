package at.SWEN2.Tourplanner.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransportType {
    DRIVING_CAR("driving-car"),
    CYCLING_REGULAR("cycling-regular"),
    FOOT_WALKING("foot-walking");

    private final String value;

    TransportType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TransportType forValue(String value) {
        for (TransportType type : TransportType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid transport type: " + value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
