package net.eightytwenty.rtd.rtdapi;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.transit.realtime.GtfsRealtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.codehaus.groovy.runtime.InvokerHelper.asList;
import static org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter.PROTOBUF;

@Component
public class RTDInterface {
    private RestTemplate restTemplate;
    private String rtdHost;

    public RTDInterface(@Autowired RestTemplate restTemplate, @Value("${rtdHost}") String rtdHost) {
        this.restTemplate = restTemplate;
        this.rtdHost = rtdHost;
    }

    public int getRealtime() throws InvalidProtocolBufferException {
        // doesn't work, RTD sends content-type as text/plain and the message converter tries to convert as
        // text format.
        ResponseEntity<GtfsRealtime.FeedMessage> rtdRealtimeFeed = restTemplate.getForEntity(
                rtdHost + "/google_sync/TripUpdate.pb", GtfsRealtime.FeedMessage.class);
        return rtdRealtimeFeed.getBody().getEntityCount();

/*
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(asList(MediaType.TEXT_PLAIN));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> rtdRealtimeFeed = restTemplate.exchange(
                rtdHost + "/google_sync/TripUpdate.pb",
                HttpMethod.GET, entity, byte[].class, "1");


        GtfsRealtime.FeedMessage feedMessage = GtfsRealtime.FeedMessage.parseFrom(rtdRealtimeFeed.getBody());
        return feedMessage.getEntityCount();
         */
    }

    public void buildMatrix(GtfsRealtime.FeedMessage feedMessage) {
    }



}
