package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.dto.Coordinates;
import at.SWEN2.Tourplanner.dto.RouteInfo;
import at.SWEN2.Tourplanner.dto.RouteRequest;
import at.SWEN2.Tourplanner.dto.RouteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class MapImageService {

    private static final Logger logger = LoggerFactory.getLogger(MapImageService.class);
    private static final String ORS_API_KEY = "5b3ce3597851110001cf6248bc797bfd5e6d43bc8245ff9754a1bfe3";
    private static final String ORS_API_URL = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";

    public RouteInfo getRouteInfo(Coordinates from, Coordinates to) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        RouteRequest request = new RouteRequest(from, to);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", ORS_API_KEY);

        HttpEntity<RouteRequest> entity = new HttpEntity<>(request, headers);

        logger.info("Request Body: {}", new ObjectMapper().writeValueAsString(request));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ORS_API_URL, entity, String.class);

        logger.info("Received response from ORS API: {}", responseEntity.getBody());

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException("Received error response from ORS API: " + responseEntity.getBody());
        }

        ObjectMapper mapper = new ObjectMapper();
        RouteResponse response = mapper.readValue(responseEntity.getBody(), RouteResponse.class);

        if (response.getFeatures() == null || response.getFeatures().isEmpty() || response.getFeatures().get(0).getProperties() == null) {
            throw new IllegalStateException("No routes found in the ORS API response");
        }

        RouteInfo routeInfo = response.getFeatures().get(0).getProperties();
        routeInfo.setGeometry(response.getFeatures().get(0).getGeometry());

        return routeInfo;
    }

    public void downloadMapImage(RouteInfo routeInfo, String imagePath) throws IOException, InterruptedException {
        List<List<Double>> coordinates = routeInfo.getGeometry().getCoordinates();
        String coordinatesJson = new ObjectMapper().writeValueAsString(coordinates);

        ProcessBuilder processBuilder = new ProcessBuilder("node", "generateMapImage.js", coordinatesJson, imagePath);
        processBuilder.directory(new File("C:\\Users\\Johannes Henz\\Desktop\\Tourplanner_Backend\\Tourplanner"));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IllegalStateException("Error generating map image, exit code: " + exitCode);
        }
    }

    public static void main(String[] args) throws Exception {
        MapImageService service = new MapImageService();
        Coordinates from = new Coordinates(48.213168, 16.341181);
        Coordinates to = new Coordinates(48.239431, 16.384729);
        RouteInfo routeInfo = service.getRouteInfo(from, to);
        String imagePath = "C:\\Users\\Johannes Henz\\Desktop\\Tourplanner_Backend\\Tourplanner\\src\\main\\java\\at\\SWEN2\\Tourplanner\\images\\route_map.png";
        service.downloadMapImage(routeInfo, imagePath);
    }
}
