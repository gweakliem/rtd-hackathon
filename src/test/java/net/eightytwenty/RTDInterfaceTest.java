package net.eightytwenty;

import org.junit.Test;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Created by gordon on 9/14/16.
 */
public class RTDInterfaceTest {

    @Test
    public void testRtdInterface() {
        RestTemplate restTemplate = new RestTemplate(asList(new ProtobufHttpMessageConverter()));
        String username = "RTDgtfsRT";
        String password = "realT!m3Feed";

        List<ClientHttpRequestInterceptor> interceptors = Collections
                .singletonList(new BasicAuthorizationInterceptor(
                        username, password));
        restTemplate.setRequestFactory(new InterceptingClientHttpRequestFactory(restTemplate.getRequestFactory(),
                interceptors));
        RTDInterface target = new RTDInterface(restTemplate, "http://www.rtd-denver.com");

        assertTrue(target.getRealtime() > 0);
    }
}