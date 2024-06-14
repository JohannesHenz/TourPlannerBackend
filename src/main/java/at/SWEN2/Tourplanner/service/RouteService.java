package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ors.api.key}")
    private String orsApiKey;

    @Value("${osm.api.url}")
    private String osmApiUrl;

    public RouteResponse getRoute(RouteRequest request) {
        List<Double> fromCoordsList = request.getFrom();
        List<Double> toCoordsList = request.getTo();

        Coordinates fromCoords = new Coordinates(fromCoordsList.get(1), fromCoordsList.get(0));
        Coordinates toCoords = new Coordinates(toCoordsList.get(1), toCoordsList.get(0));

        RouteInfo routeInfo = getRouteInfo(fromCoords, toCoords);

        String mapImageUrl = getMapImageUrl(routeInfo);

        RouteResponse response = new RouteResponse();
        response.setEstimatedTime(routeInfo.getSummary().getDuration());
        response.setDistance(routeInfo.getSummary().getDistance());
        response.setMapImageUrl(mapImageUrl);

        return response;
    }

    private Coordinates getCoordinates(String location) {
        String url = String.format("https://api.openrouteservice.org/geocode/search?api_key=%s&text=%s", orsApiKey, location);
        GeocodeResponse geocodeResponse = restTemplate.getForObject(url, GeocodeResponse.class);
        return (Coordinates) geocodeResponse.getFeatures().get(0).getGeometry().getCoordinates();
    }

    private RouteInfo getRouteInfo(Coordinates from, Coordinates to) {
        String url = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", orsApiKey);

        Map<String, List<List<Double>>> body = new HashMap<>();
        body.put("coordinates", Arrays.asList(
                Arrays.asList(from.getLongitude(), from.getLatitude()),
                Arrays.asList(to.getLongitude(), to.getLatitude())
        ));

        HttpEntity<Map<String, List<List<Double>>>> request = new HttpEntity<>(body, headers);
        RouteResponse response = restTemplate.postForObject(url, request, RouteResponse.class);

        if (response == null || response.getFeatures() == null || response.getFeatures().isEmpty()) {
            throw new IllegalStateException("No routes found in the ORS API response (RouteService)");
        }

        return response.getFeatures().get(0).getProperties();
    }

    private String getMapImageUrl(RouteInfo routeInfo) {
        List<List<Double>> coordinates = routeInfo.getGeometry().getCoordinates();
        String coords = coordinates.stream()
                .map(coord -> coord.get(1) + "," + coord.get(0))
                .collect(Collectors.joining("|"));

        return String.format("%s?polyline=%s", osmApiUrl, coords);
    }
}
