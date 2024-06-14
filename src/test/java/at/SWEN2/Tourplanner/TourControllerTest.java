package at.SWEN2.Tourplanner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import at.SWEN2.Tourplanner.controller.TourController;
import at.SWEN2.Tourplanner.model.RouteInfo;
import at.SWEN2.Tourplanner.model.Tour;
import at.SWEN2.Tourplanner.service.RouteService;
import at.SWEN2.Tourplanner.service.TourService;
import at.SWEN2.Tourplanner.utility.OSMClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.awt.image.BufferedImage;

@WebMvcTest(TourController.class)
public class TourControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TourService tourService;

    @MockBean
    private RouteService routeService;

    @MockBean
    private OSMClient osmClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        // Mock the RouteService response
        RouteInfo mockRouteInfo = new RouteInfo();
        mockRouteInfo.setDistance(1000.0);
        mockRouteInfo.setDuration(600.0);
        mockRouteInfo.setGeometry("[ [12.4924, 41.8902], [12.4944, 41.8902] ]");
        when(routeService.getRouteInfo(anyString(), anyString())).thenReturn(mockRouteInfo);

        // Mock the OSMClient response
        when(osmClient.getMapImage(anyList())).thenReturn(new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB));
    }

    @Test
    void testCreateTour() throws Exception {
        Tour tour = new Tour();
        tour.setName("River Cruise");
        tour.setDescription("A relaxing cruise along the river.");
        tour.setFromLocation("Pier 1");
        tour.setToLocation("Pier 2");
        tour.setTransportType("Boat");

        // Mock the tourService.saveTour method
        when(tourService.saveTour(any(Tour.class))).thenReturn(tour);

        mockMvc.perform(post("/api/tours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tour)))
                .andExpect(status().isCreated());  // Change this to expect 201 Created status
    }
}
