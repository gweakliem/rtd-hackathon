package net.eightytwenty.rtd.config;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sun.security.acl.PrincipalImpl;

import java.security.Principal;
import java.util.Arrays;

/**
 * Created by gordon on 9/14/16.
 */
@Configuration
public class RtdApplicationConfiguration {
    @Bean
    RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
        return new RestTemplate(Arrays.asList(hmc));
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    CredentialsProvider credentialsProvider() {
        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope("rtd-denver.com", 80),
                new Credentials() {
                    @Override
                    public Principal getUserPrincipal() {
                        return new PrincipalImpl("RTDgtfsRT");
                    }

                    @Override
                    public String getPassword() {
                        return "realT!m3Fee";
                    }
                });
        return provider;
    }
}
