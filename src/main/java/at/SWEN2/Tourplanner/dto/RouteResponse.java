package at.SWEN2.Tourplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteResponse {

    @JsonProperty("features")
    private List<Feature> features;

    private double estimatedTime;
    private double distance;
    private String mapImageUrl;

    @Override
    public String toString() {
        return "RouteResponse{" +
                "features=" + features +
                ", estimatedTime=" + estimatedTime +
                ", distance=" + distance +
                ", mapImageUrl='" + mapImageUrl + '\'' +
                '}';
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feature {
        @JsonProperty("properties")
        private RouteInfo properties;

        @JsonProperty("geometry")
        private Geometry geometry;
    }
    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Properties {
        private List<Segment> segments;

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


    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
        @JsonProperty("coordinates")
        private List<List<Double>> coordinates;
    }
}
