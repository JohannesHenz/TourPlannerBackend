package at.SWEN2.Tourplanner.controller;

import at.SWEN2.Tourplanner.model.TourLog;
import at.SWEN2.Tourplanner.service.TourLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-logs")
public class TourLogController {
    @Autowired
    private TourLogService tourLogService;

    @GetMapping
    public List<TourLog> getAllTourLogs(@RequestParam Long tourId) {
        return tourLogService.getTourLogsByTourId(tourId);
    }

    @PostMapping
    public TourLog createTourLog(@RequestBody TourLog tourLog) {
        return tourLogService.saveTourLog(tourLog);
    }

    @PutMapping("/{id}")
    public TourLog updateTourLog(@PathVariable Long id, @RequestBody TourLog tourLog) {
        tourLog.setId(id);
        return tourLogService.saveTourLog(tourLog);
    }

    @DeleteMapping("/{id}")
    public void deleteTourLog(@PathVariable Long id) {
        tourLogService.deleteTourLog(id);
    }

    // Additional endpoints...
}
