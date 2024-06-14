package at.SWEN2.Tourplanner.utility;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.util.Base64;
import java.util.List;

public class OSMClient {
    public BufferedImage getMapImage(List<double[]> coordinates) throws Exception {
        // Create a URL for your map image with route overlay
        String imageUrl = createMapImageUrl(coordinates);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);

        byte[] imageBytes = response.getBody();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bis);
    }

    private String createMapImageUrl(List<double[]> coordinates) {
        // Create the URL to fetch the map image from OSM with route overlay
        // This is an example; you'll need to properly format the URL for your use case
        StringBuilder sb = new StringBuilder("https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/");
        for (double[] coordinate : coordinates) {
            sb.append(coordinate[0]).append(",").append(coordinate[1]).append(",");
        }
        sb.append("/auto/500x300?access_token=your_mapbox_token");
        return sb.toString();
    }
}
