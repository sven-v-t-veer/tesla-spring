package be.sven.tesla.restclient.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
public class UrlBuilder {

    private final String baseUrl;
    private final String vehiclesUrl;

    @Autowired
    public UrlBuilder(@Value("${com.tesla.baseUrl}")String baseUrl, @Value("${com.tesla.vehiclesUrl}")String vehiclesUrl) {
        this.baseUrl = baseUrl;
        this.vehiclesUrl = vehiclesUrl;
    }

    public String buildUrl() {
        return baseUrl + vehiclesUrl;
    }

    public String buildUrl(Long id) {
        return id != null ? buildUrl() + "/" + id : buildUrl();
    }

    public String buildUrl(Long id, Command command) {
        String url =  buildUrl(id) + command;
        return UriComponentsBuilder.fromUriString(url).build().toUriString();
    }

    public String buildUrl(Long id, Command command, Map<String, String> parameters) {
        String url =  buildUrl(id) + command;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        parameters.forEach(builder::queryParam);
        return builder.build().toUriString();
    }
}
