package at.SWEN2.Tourplanner.controller;

import at.SWEN2.Tourplanner.dto.RouteRequest;
import at.SWEN2.Tourplanner.dto.RouteResponse;
import at.SWEN2.Tourplanner.model.Tour;
import at.SWEN2.Tourplanner.service.MapImageService;
import at.SWEN2.Tourplanner.service.RouteService;
import at.SWEN2.Tourplanner.service.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourControllerTest {

    @Mock
    private TourService tourService;

    @Mock
    private RouteService routeService;



    @Mock
    private MapImageService mapImageService;

    @InjectMocks
    private TourController tourController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTour() {
        Tour tour = new Tour();
        tour.setFromLocation("Vienna, Austria");
        tour.setToLocation("Graz, Austria");
        tour.setTransportType("driving-car");

        when(tourService.saveTour(tour)).thenReturn(tour);
        when(routeService.getRoute(any(RouteRequest.class))).thenReturn(new RouteResponse());

        ResponseEntity<Tour> response = tourController.createTour(tour);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(tourService, times(1)).saveTour(tour);
        verify(routeService, times(1)).getRoute(any(RouteRequest.class));
    }


    @Test
    void testDeleteTour() {
        String tourId = "1";
        doNothing().when(tourService).deleteTour(tourId);

        ResponseEntity<Void> response = tourController.deleteTour(tourId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tourService, times(1)).deleteTour(tourId);
    }


    @Test
    void testGetAllTours() {
        List<Tour> tourList = new ArrayList<>();
        tourList.add(new Tour());
        tourList.add(new Tour());

        when(tourService.getAllTours()).thenReturn(tourList);

        List<Tour> result = tourController.getAllTours();

        assertEquals(2, result.size());
    }

    @Test
    void testCreateTourWithNullFields() {
        Tour tour = new Tour();

        assertThrows(NullPointerException.class, () -> tourController.createTour(tour));
    }
}
