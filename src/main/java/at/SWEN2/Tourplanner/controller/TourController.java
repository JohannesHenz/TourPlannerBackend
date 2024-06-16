package at.SWEN2.Tourplanner.controller;

import at.SWEN2.Tourplanner.dto.RouteRequest;
import at.SWEN2.Tourplanner.dto.RouteResponse;
import at.SWEN2.Tourplanner.model.Tour;
import at.SWEN2.Tourplanner.model.TourLog;
import at.SWEN2.Tourplanner.service.MapImageService;
import at.SWEN2.Tourplanner.service.RouteService;
import at.SWEN2.Tourplanner.service.TourLogService;
import at.SWEN2.Tourplanner.service.TourService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private ResourceLoader resourceLoader;


    private static final Logger logger = LoggerFactory.getLogger(MapImageService.class);


    @Autowired
    private TourService tourService;
    @Autowired
    private TourLogService tourLogService; // Inject the TourLogService
    @Autowired
    private RouteService routeService;
    @Autowired
    private MapImageService mapImageService;

    // GET all tours
    @GetMapping
    public List<Tour> getAllTours() {
        return tourService.getAllTours();
    }

    // POST a new tour
    @PostMapping
    public ResponseEntity<Tour> createTour(@RequestBody Tour tour) {
        Tour createdTour = tourService.saveTour(tour);
        processRouteAndImage(createdTour);
        return new ResponseEntity<>(createdTour, HttpStatus.CREATED);
    }

    // POST a new TourLog for a specific tour
    @PostMapping("/{tourId}/logs")
    public ResponseEntity<TourLog> createTourLog(@PathVariable String tourId, @RequestBody TourLog tourLog) {
        Tour tour = tourService.getTourById(tourId);
        tourLog.setTour(tour);
        TourLog createdTourLog = tourLogService.saveTourLog(tourLog);
        return new ResponseEntity<>(createdTourLog, HttpStatus.CREATED);
    }



    // PUT (update) a specific tour
    @PutMapping("/{tourId}")
    public ResponseEntity<Tour> updateTour(@PathVariable String tourId, @RequestBody Tour tour) {
        Tour existingTour = tourService.getTourById(tourId);
        if (existingTour == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingTour.setName(tour.getName());
        existingTour.setDescription(tour.getDescription());
        existingTour.setFromLocation(tour.getFromLocation());
        existingTour.setToLocation(tour.getToLocation());
        existingTour.setTransportType(tour.getTransportType());
        existingTour.setDistance(tour.getDistance());
        existingTour.setEstimatedTime(tour.getEstimatedTime());

        // Store the old image path before updating
        String oldImagePath = existingTour.getMapImageUrl();

        // Process the route and image and set the new image path
        processRouteAndImage(existingTour);

        // Delete the old image if it is different from the new one
        if (oldImagePath != null && !oldImagePath.equals(existingTour.getMapImageUrl())) {
            mapImageService.deleteImage(oldImagePath);
        }

        Tour updatedTour = tourService.saveTour(existingTour);
        return new ResponseEntity<>(updatedTour, HttpStatus.OK);
    }




    // PUT (update) a specific TourLog for a specific tour
    // PUT (update) a specific TourLog for a specific tour
    @PutMapping("/{tourId}/{logId}")
    public ResponseEntity<TourLog> updateTourLog(@PathVariable String tourId, @PathVariable String logId, @RequestBody TourLog tourLog) {
        // Fetch the existing TourLog from the database
        TourLog existingTourLog = tourLogService.getTourLogById(logId);
        if (existingTourLog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the properties of the existing TourLog
        existingTourLog.setDate(tourLog.getDate());
        existingTourLog.setTime(tourLog.getTime());
        existingTourLog.setComment(tourLog.getComment());
        existingTourLog.setDifficulty(tourLog.getDifficulty());
        existingTourLog.setTotalDistance(tourLog.getTotalDistance());
        existingTourLog.setTotalTime(tourLog.getTotalTime());
        existingTourLog.setRating(tourLog.getRating());

        // Save the updated TourLog
        TourLog updatedTourLog = tourLogService.saveTourLog(existingTourLog);

        return new ResponseEntity<>(updatedTourLog, HttpStatus.OK);
    }

    // DELETE a specific tour
    @DeleteMapping("/{tourId}")
    public ResponseEntity<Void> deleteTour(@PathVariable String tourId) {
        tourService.deleteTour(tourId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // DELETE a specific TourLog for a specific tour
    @DeleteMapping("/{tourId}/{logId}")
    public ResponseEntity<Void> deleteTourLog(@PathVariable String tourId, @PathVariable String logId) {
        tourLogService.deleteTourLog(tourId, logId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    // GET the image of a specific tour
    @GetMapping("/{tourId}")
    public ResponseEntity<Tour> getTourById(@PathVariable String tourId) {
        Tour tour = tourService.getTourById(tourId);
        if (tour != null) {
            String imagePath = "C:\\Users\\Johannes Henz\\Desktop\\Tourplanner_Backend\\Tourplanner\\src\\main\\java\\at\\SWEN2\\Tourplanner\\images" + tour.getId() + ".png"; // Adjust path as needed
            tour.setMapImageUrl(imagePath);
            return ResponseEntity.ok(tour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

*/
    //todo: make it work to send the image to the frontend
    @GetMapping("/{tourId}")
    public ResponseEntity<Tour> getTourById(@PathVariable String tourId) {
        Tour tour = tourService.getTourById(tourId);
        if (tour != null) {
            String imagePath = "C:\\Users\\Johannes Henz\\Desktop\\Tourplanner_Backend\\Tourplanner\\src\\main\\java\\at\\SWEN2\\Tourplanner\\images" + tour.getId() + ".png"; // Adjust path as needed
            tour.setMapImageUrl(imagePath);
            return ResponseEntity.ok(tour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET the image of a specific tour
    @GetMapping("/{tourId}/image")
    public ResponseEntity<Resource> getTourImage(@PathVariable String tourId) {
        Tour tour = tourService.getTourById(tourId);
        if (tour != null) {
            String basePath = Paths.get("").toAbsolutePath().toString();
            String imagePath = "file:" + basePath + "/src/main/java/at/SWEN2/Tourplanner/images" + tour.getId() + ".png";
            Resource imageResource = resourceLoader.getResource(imagePath);
            if (imageResource.exists()) {
                logger.info("Image resource: " + imageResource);
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(imageResource);
            } else {
                logger.error("Image not found at path: " + imagePath);
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    private void processRouteAndImage(Tour tour) {
        if (tour.getFromLocation() != null && tour.getToLocation() != null && tour.getTransportType() != null) {
            RouteRequest routeRequest = new RouteRequest();
            routeRequest.setFrom(tour.getFromLocation());
            routeRequest.setTo(tour.getToLocation());
            routeRequest.setTransportType(tour.getTransportType());

            RouteResponse routeResponse = routeService.getRoute(routeRequest);

            logger.info("RouteResponse: " + routeResponse.toString());

            if (routeResponse != null && routeResponse.getFeatures() != null && !routeResponse.getFeatures().isEmpty()) {
                RouteResponse.Feature feature = routeResponse.getFeatures().get(0);
                if (feature != null && feature.getProperties() != null && feature.getProperties().getSummary() != null) {
                    logger.info("Distance: " + feature.getProperties().getSummary().getDistance());
                    logger.info("Duration: " + feature.getProperties().getSummary().getDuration());

                    tour.setDistance(feature.getProperties().getSummary().getDistance());
                    tour.setEstimatedTime(feature.getProperties().getSummary().getDuration());

                    String imagePath = "C:\\Users\\Johannes Henz\\Desktop\\Tourplanner_Backend\\Tourplanner\\src\\main\\java\\at\\SWEN2\\Tourplanner\\images" + tour.getId() + ".png"; // Adjust path as needed
                    try {
                        mapImageService.downloadMapImage(feature.getProperties(),feature.getGeometry(), imagePath);
                        tour.setMapImageUrl(imagePath);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.error("Feature, Properties, Summary or Geometry is null");
                }
            } else {
                logger.error("RouteResponse or Features is null");
            }
        } else {
            logger.error("FromLocation, ToLocation or TransportType is null");
        }
    }

    // Other methods...
}
