package at.SWEN2.Tourplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteResponse {

    @JsonProperty("features")
    private List<Feature> features;

    private double estimatedTime;
    private double distance;
    private String mapImageUrl;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getMapImageUrl() {
        return mapImageUrl;
    }

    public void setMapImageUrl(String mapImageUrl) {
        this.mapImageUrl = mapImageUrl;
    }

    @Override
    public String toString() {
        return "RouteResponse{" +
                "features=" + features +
                ", estimatedTime=" + estimatedTime +
                ", distance=" + distance +
                ", mapImageUrl='" + mapImageUrl + '\'' +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feature {

        @JsonProperty("properties")
        private RouteInfo properties;

        @JsonProperty("geometry")
        private RouteInfo.Geometry geometry;

        public RouteInfo getProperties() {
            return properties;
        }

        public void setProperties(RouteInfo properties) {
            this.properties = properties;
        }

        public RouteInfo.Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(RouteInfo.Geometry geometry) {
            this.geometry = geometry;
        }
    }
}
