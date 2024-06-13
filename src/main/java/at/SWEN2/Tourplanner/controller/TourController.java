package at.SWEN2.Tourplanner.controller;

import at.SWEN2.Tourplanner.model.Tour;
import at.SWEN2.Tourplanner.model.TourLog;
import at.SWEN2.Tourplanner.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private final TourService tourService;


    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public List<Tour> getAllTours() {
        return tourService.getAllTours();
    }

    @GetMapping("/{id}")
    public Tour getTourById(@PathVariable Long id) {
        return tourService.getTourById(id);
    }

    @PostMapping
    public ResponseEntity<Tour> createTour(@RequestBody Tour tour) {
        Tour createdTour = tourService.saveTour(tour);
        return new ResponseEntity<>(createdTour, HttpStatus.CREATED);
    }

    @PostMapping("/{tourId}/logs")
    public ResponseEntity<TourLog> addTourLog(@PathVariable Long tourId, @RequestBody TourLog tourLog) {
        TourLog createdTourLog = tourService.addTourLog(tourId, tourLog);
        return new ResponseEntity<>(createdTourLog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Tour updateTour(@PathVariable Long id, @RequestBody Tour tour) {
        tour.setId(id);
        return tourService.saveTour(tour);
    }

    @DeleteMapping("/{id}")
    public void deleteTour(@PathVariable Long id) {
        tourService.deleteTour(id);
    }

    @GetMapping("/search")
    public List<Tour> searchTours(@RequestParam String keyword) {
        return tourService.searchTours(keyword);
    }

    // Additional endpoints...
}
