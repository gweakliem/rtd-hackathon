package net.eightytwenty;

import com.google.transit.realtime.GtfsRealtime;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by jbeeby on 9/13/16.
 */
public class DataPuller {

    public static List<Long> decode(InputStream is){
        try {
            final GtfsRealtime.FeedMessage feedMessage = GtfsRealtime.FeedMessage.parseFrom(is);

            final List<GtfsRealtime.FeedEntity> entityList = feedMessage.getEntityList();

            List<List<GtfsRealtime.TripUpdate.StopTimeUpdate>> routesAtStop = entityList
                    .stream()
                    .filter(feedEntity -> feedEntity.getTripUpdate().getTrip().getRouteId().equals("10")
                                && feedEntity.getTripUpdate().getStopTimeUpdateList().stream().anyMatch(p -> p.getStopId().equals("14965")))
                    .map(f -> f.getTripUpdate().getStopTimeUpdateList())
                    .collect(toList());

            List<GtfsRealtime.TripUpdate.StopTimeEvent> departures = routesAtStop.stream()
                    .map(st -> st.stream().filter(ste -> ste.getStopId().equals("14965")).map(ste -> ste.getDeparture()).collect(toList()))
                    .flatMap(f -> f.stream())
                    .collect(toList());

            long now = System.currentTimeMillis();
            List<Long> fromNow = departures.stream().map(f ->  f.getTime() * 1000 - now).collect(toList());
            System.out.println("departures = " + departures);
            return fromNow;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream download() throws Exception {
        HttpHost target = new HttpHost("www.rtd-denver.com", 80, "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(target.getHostName(), target.getPort()),
                new UsernamePasswordCredentials("RTDgtfsRT", "realT!m3Feed"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        try {

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local
            // auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(target, basicAuth);

            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);

            HttpGet httpget = new HttpGet("http://www.rtd-denver.com/google_sync/TripUpdate.pb");

            System.out.println("Executing request " + httpget.getRequestLine() + " to target " + target);
//            for (int i = 0; i < 3; i++) {
                CloseableHttpResponse response = httpclient.execute(target, httpget, localContext);

                final InputStream inputStream = response.getEntity().getContent();
                decode(inputStream);
                return inputStream;


//            }
        } finally {
            httpclient.close();
        }

    }
}
