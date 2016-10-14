package net.eightytwenty;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.transit.realtime.GtfsRealtime;
import net.eightytwenty.rtd.rtdapi.RTDInterface;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 */
public class RTDInterfaceTest {
    RestTemplate restTemplate;

    @Test
    public void testRtdInterface() throws InvalidProtocolBufferException {
        restTemplate =  mock(RestTemplate.class);
                //new RestTemplate(asList(new ByteArrayHttpMessageConverter()));
        // Message converter doesn't work. RTD claims text/plain but it's actually application/protobuf
                //new RestTemplate(asList(new ProtobufHttpMessageConverter()));
        String username = "RTDgtfsRT";
        String password = "realT!m3Feed";

/*
        List<ClientHttpRequestInterceptor> interceptors = Collections
                .singletonList(new BasicAuthorizationInterceptor(
                        username, password));
        restTemplate.setRequestFactory(new InterceptingClientHttpRequestFactory(restTemplate.getRequestFactory(),
                interceptors));
*/
        //GtfsRealtime.FeedMessage feedMessage =  mock(GtfsRealtime.FeedMessage.class);
        //when(feedMessage.getEntityCount()).thenReturn(23);
        ResponseEntity<GtfsRealtime.FeedMessage> rtdRealtimeFeed = new ResponseEntity<>(
                GtfsRealtime.FeedMessage.getDefaultInstance(), HttpStatus.OK
        );
        when(restTemplate.getForEntity(
                "http://www.rtd-denver.com/google_sync/TripUpdate.pb", GtfsRealtime.FeedMessage.class))
        .thenReturn(rtdRealtimeFeed);

        RTDInterface target = new RTDInterface(restTemplate, "http://www.rtd-denver.com");

        assertThat(target.getRealtime(), equalTo(0));
        verify(restTemplate)
                .getForEntity(eq("http://www.rtd-denver.com/google_sync/TripUpdate.pb"), eq(GtfsRealtime.FeedMessage.class));
    }
}