package at.SWEN2.Tourplanner.service;

import at.SWEN2.Tourplanner.dto.RouteRequest;
import at.SWEN2.Tourplanner.dto.RouteResponse;
import at.SWEN2.Tourplanner.dto.RouteResponse.Feature;
import at.SWEN2.Tourplanner.dto.RouteResponse.Geometry;
import at.SWEN2.Tourplanner.dto.GeocodeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RouteServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RouteService routeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }




    @Test
    void testGetRouteThrowsIllegalStateException() {
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setFrom("Vienna, Austria");
        routeRequest.setTo("Graz, Austria");
        routeRequest.setTransportType("driving-car");

        when(restTemplate.getForObject(anyString(), any())).thenThrow(new IllegalStateException("ORS API error"));

        assertThrows(IllegalStateException.class, () -> routeService.getRoute(routeRequest));
    }
}
