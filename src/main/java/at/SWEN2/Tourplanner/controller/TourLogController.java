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

    @PostMapping
    public TourLog createTourLog(@RequestBody TourLog tourLog) {
        return tourLogService.saveTourLog(tourLog);
    }

    @PutMapping("/{id}")
    public TourLog updateTourLog(@PathVariable String id, @RequestBody TourLog tourLog) {
        tourLog.setLogId(id);
        return tourLogService.saveTourLog(tourLog);
    }


    @DeleteMapping("/{id}")
    public void deleteTourLog(@PathVariable String tourId, @PathVariable String logId) {
        tourLogService.deleteTourLog(tourId, logId);
    }

}
