package at.SWEN2.Tourplanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import at.SWEN2.Tourplanner.utility.OSMClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;
import org.mockito.MockitoAnnotations;

public class OSMClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OSMClient osmClient;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMapImage() throws Exception {
        // Mock the API response
        BufferedImage mockImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        when(restTemplate.getForObject(anyString(), eq(BufferedImage.class))).thenReturn(mockImage);

        List<double[]> coordinates = Arrays.asList(
                new double[]{12.4924, 41.8902},
                new double[]{12.4944, 41.8902}
        );

        BufferedImage image = osmClient.getMapImage(coordinates);

        assertNotNull(image);
        assertEquals(100, image.getWidth());
        assertEquals(100, image.getHeight());
    }
}
