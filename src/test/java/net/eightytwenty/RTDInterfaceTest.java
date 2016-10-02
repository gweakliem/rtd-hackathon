package net.eightytwenty;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.protobuf.ExtensionRegistryInitializer;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 *
 */
public class RTDInterfaceTest {

    @Test
    public void testRtdInterface() throws InvalidProtocolBufferException {
        RestTemplate restTemplate = new RestTemplate(asList(new ByteArrayHttpMessageConverter()));
        // Message converter doesn't work. RTD claims text/plain but it's actually application/protobuf
                //new RestTemplate(asList(new ProtobufHttpMessageConverter()));
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