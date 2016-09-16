package net.eightytwenty;

import com.google.transit.realtime.GtfsRealtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by gordon on 9/14/16.
 */
public class RTDInterface {
    private RestTemplate restTemplate;
    private String rtdHost;

    public RTDInterface(@Autowired RestTemplate restTemplate, @Value("${rtdHost}") String rtdHost) {
        this.restTemplate = restTemplate;
        this.rtdHost = rtdHost;
    }

    public int getRealtime() {
        /*
        URL url = new URL("URL OF YOUR GTFS-REALTIME SOURCE GOES HERE");
    FeedMessage feed = FeedMessage.parseFrom(url.openStream());
    for (FeedEntity entity : feed.getEntityList()) {
      if (entity.hasTripUpdate()) {
        System.out.println(entity.getTripUpdate());
      }
    }
         */
        ResponseEntity<GtfsRealtime.FeedMessage> rtdRealtimeFeed = restTemplate.getForEntity(
                rtdHost + "/google_sync/TripUpdate.pb", GtfsRealtime.FeedMessage.class);

        return rtdRealtimeFeed.getBody().getEntityCount();
    }

}
