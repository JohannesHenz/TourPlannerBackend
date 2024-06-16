package at.SWEN2.Tourplanner.controller;

import at.SWEN2.Tourplanner.dto.RouteRequest;
import at.SWEN2.Tourplanner.dto.RouteResponse;
import at.SWEN2.Tourplanner.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@RequestBody RouteRequest request) {
        RouteResponse response = routeService.getRoute(request);
        return ResponseEntity.ok(response);
    }
}
