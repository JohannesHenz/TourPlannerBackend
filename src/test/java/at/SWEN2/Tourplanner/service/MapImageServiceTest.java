package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.dto.GeocodeResponse;
import at.SWEN2.Tourplanner.dto.RouteRequest;
import at.SWEN2.Tourplanner.dto.RouteResponse;
import at.SWEN2.Tourplanner.dto.RouteResponse.Geometry;
import at.SWEN2.Tourplanner.service.RouteService;
import at.SWEN2.Tourplanner.dto.RouteInfo;
import at.SWEN2.Tourplanner.model.Tour;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MapImageServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MapImageService mapImageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testDeleteImage() throws IOException {
        // Arrange
        String basePath = Paths.get("").toAbsolutePath().toString();
        String imagePath = basePath + "/src/test/resources/" + "testImage.png";

        // Create an empty PNG file
        File file = new File(imagePath);
        if (!file.exists()) {
            Files.createFile(Paths.get(imagePath));
        }
        assertTrue(file.exists());

        // Act
        mapImageService.deleteImage(imagePath);

        // Assert
        assertFalse(file.exists());
    }
    @Test
    void testDownloadMapImage() throws IOException, InterruptedException {
        RouteInfo routeInfo = new RouteInfo();
        Geometry geometry = new Geometry();
        geometry.setCoordinates(new ArrayList<>());

        String basePath = Paths.get("").toAbsolutePath().toString();
        String imagePath = basePath + "/src/test/resources/" + UUID.randomUUID() + ".png";

        doNothing().when(objectMapper).writeValue(any(File.class), any());

        mapImageService.downloadMapImage(routeInfo, geometry, imagePath);

        File file = new File(imagePath);
        assertTrue(file.exists());

        // Clean up downloaded image after the test
        Files.deleteIfExists(Paths.get(imagePath));
    }

}
