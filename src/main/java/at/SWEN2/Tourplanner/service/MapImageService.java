package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.dto.RouteInfo;
import at.SWEN2.Tourplanner.dto.RouteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class MapImageService {

    private static final Logger logger = LoggerFactory.getLogger(MapImageService.class);

    public void downloadMapImage(RouteInfo routeInfo, RouteResponse.Geometry geometry, String imagePath) throws IOException, InterruptedException {
        if (routeInfo == null) {
            logger.error("RouteInfo is null");
            return;
        }
        logger.info("Routeinfo: " + routeInfo.toString());
        logger.info("Geometry: " + geometry);
        if (geometry == null) {
            logger.error("Geometry is null 27");
            return;
        }

        List<List<Double>> coordinates = geometry.getCoordinates();
        if (coordinates == null) {
            logger.error("Coordinates are null 33");
            return;
        }

        String coordinatesJson = new ObjectMapper().writeValueAsString(coordinates);

        // Log the coordinates JSON
        logger.debug("Coordinates JSON: {}", coordinatesJson);

        ProcessBuilder processBuilder = new ProcessBuilder("node", "generateMapImage.js", coordinatesJson, imagePath);
        processBuilder.directory(new File("C:\\Users\\Johannes Henz\\Desktop\\Tourplanner_Backend\\Tourplanner"));
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IllegalStateException("Error generating map image, exit code: " + exitCode);
        }
    }


    public void deleteImage(String imagePath) {
        if (imagePath != null) {
            File file = new File(imagePath);
            if (file.exists()) {
                if (file.delete()) {
                    logger.info("Deleted old image: " + imagePath);
                } else {
                    logger.error("Failed to delete old image: " + imagePath);
                }
            } else {
                logger.warn("Old image not found: " + imagePath);
            }
        }
    }


}