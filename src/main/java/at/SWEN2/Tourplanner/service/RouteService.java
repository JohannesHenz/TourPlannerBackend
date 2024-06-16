package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private static final Logger logger = LogManager.getLogger(RouteService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ors.api.key}")
    private String orsApiKey;

    public RouteResponse getRoute(RouteRequest request) {
        List<Double> fromCoords = getCoordinates(request.getFrom());
        List<Double> toCoords = getCoordinates(request.getTo());


        String fromCoordsString = String.join(",", fromCoords.stream().map(Object::toString).collect(Collectors.toList()));
        String toCoordsString = String.join(",", toCoords.stream().map(Object::toString).collect(Collectors.toList()));

        logger.info("Transport type: " + request.getTransportType());

        //bastel die URL zusammen
        String url = "https://api.openrouteservice.org/v2/directions/" +
                request.getTransportType() +
                "?start=" +
                fromCoordsString +
                "&end=" +
                toCoordsString;

        logger.info("URL: " + url);

        //setze die Header Variablen
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(orsApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers); //Wir holen uns die Headers

        logger.info("Request: " + request.toString());


        try {
            ResponseEntity<RouteResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, RouteResponse.class); //Wir machen den Request
            RouteResponse response = responseEntity.getBody(); //Wir holen uns den Body der Response
            logger.info("Response: " + response);

            if (response == null) {
                logger.error("No response from the ORS API");
                throw new IllegalStateException("No response from the ORS API");
            }

            if (response.getFeatures() == null || response.getFeatures().isEmpty()) {
                logger.error("No features found in the ORS API response");
                throw new IllegalStateException("No features found in the ORS API response");
            }

            for (RouteResponse.Feature feature : response.getFeatures()) {
                if (feature.getGeometry() == null) {
                    logger.error("Feature geometry is null for feature: " + feature);
                }
            }

            return response;
        } catch (Exception e) {
            logger.error("Error fetching route: ", e);
            throw e;
        }
    }

    public List<Double> getCoordinates(String location) {
        logger.info("Getting coordinates for location: " + location);
        String url = String.format("https://api.openrouteservice.org/geocode/search?api_key=%s&text=%s", orsApiKey, location);
        GeocodeResponse geocodeResponse = restTemplate.getForObject(url, GeocodeResponse.class);
        logger.info("Geocode response: " + geocodeResponse);

        if (geocodeResponse == null) {
            logger.error("No response from the ORS Geocode API");
            throw new IllegalStateException("No response from the ORS Geocode API");
        }

        if (geocodeResponse.getFeatures() == null || geocodeResponse.getFeatures().isEmpty()) {
            logger.error("No features found in the ORS Geocode API response");
            throw new IllegalStateException("No features found in the ORS Geocode API response");
        }

        GeocodeResponse.Feature firstFeature = geocodeResponse.getFeatures().get(0);
        if (firstFeature.getGeometry() == null) {
            logger.error("Geometry is null for the first feature: " + firstFeature);
            throw new IllegalStateException("Geometry is null for the first feature");
        }

        logger.info("Coordinates found: " + firstFeature.getGeometry().getCoordinates());
        return firstFeature.getGeometry().getCoordinates();
    }
}
