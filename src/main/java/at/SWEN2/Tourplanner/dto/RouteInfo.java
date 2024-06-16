package at.SWEN2.Tourplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteInfo {

    @JsonProperty("summary")
    private Summary summary;

    @JsonProperty("geometry")
    private Geometry geometry;

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Summary {
        private double distance;
        private double duration;

        public void setDuration(double duration) {
            this.duration = duration;
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
        @JsonProperty("coordinates")
        private List<List<Double>> coordinates;

        public List<List<Double>> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<List<Double>> coordinates) {
            this.coordinates = coordinates;
        }
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Segment {
        private double distance;
        private double duration;
        private List<Step> steps;

        @Setter
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Step {
            private double distance;
            private double duration;
            private String instruction;
        }
    }
}