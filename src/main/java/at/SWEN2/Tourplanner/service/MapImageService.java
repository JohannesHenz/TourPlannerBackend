package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.dto.RouteInfo;
import at.SWEN2.Tourplanner.dto.RouteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class MapImageService {

    private static final Logger logger = LogManager.getLogger(MapImageService.class);

    public void downloadMapImage(RouteInfo routeInfo, RouteResponse.Geometry geometry, String imagePath) throws IOException, InterruptedException {
        if (routeInfo == null) {
            logger.error("RouteInfo is null");
            return;
        }
        logger.info("Routeinfo: " + routeInfo.toString());
        logger.info("Geometry: " + geometry);
        if (geometry == null) {
            logger.error("Geometry is null");
            return;
        }

        List<List<Double>> coordinates = geometry.getCoordinates();
        if (coordinates == null) {
            logger.error("Coordinates are null");
            return;
        }

        String coordinatesJson = new ObjectMapper().writeValueAsString(coordinates);

        // logge die Koordinaten
        logger.debug("Coordinates JSON: {}", coordinatesJson);

        // mache einen temporäres directory um die JSON Kooridnaten zu speichern
        String basePath = Paths.get("").toAbsolutePath().toString();
        String tempDirPath = basePath + "/temp";
        File tempDir = new File(tempDirPath);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        String tempFilePath = tempDirPath + "/" + UUID.randomUUID().toString() + ".json";
        File tempFile = new File(tempFilePath);

        // Schreibe die JSON Koordinaten in die temporäre Datei
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(coordinatesJson);
        }

        ProcessBuilder processBuilder = new ProcessBuilder("node", "generateMapImage.js", tempFilePath, imagePath);
        processBuilder.directory(new File(basePath));
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

        // Lösche die temporäre JSON Datei nachdem das Bild generiert wurde
        if (!tempFile.delete()) {
            logger.warn("Failed to delete temporary file: " + tempFilePath);
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