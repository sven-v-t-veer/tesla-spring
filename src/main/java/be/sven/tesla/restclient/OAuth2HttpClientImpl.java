package be.sven.tesla.restclient;

import be.sven.tesla.model.Token;
import be.sven.tesla.spi.OAuthHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2HttpClientImpl implements OAuthHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2HttpClientImpl.class);

    private final String clientId;
    private final String clientSecret;
    private final String baseUrl;
    private final String tokenUrl;
    private final RestTemplate template;
    private final HttpHeadersBuilder headersBuilder;

    @Autowired
    public OAuth2HttpClientImpl(RestTemplate template, @Value("${com.tesla.clientId}") String clientId, @Value("${com.tesla.clientSecret}") String clientSecret,
                                @Value("${com.tesla.baseUrl}") String baseUrl, @Value("${com.tesla.tokenUrl}") String tokenUrl, HttpHeadersBuilder headersBuilder) {
        this.template = template;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.baseUrl = baseUrl;
        this.tokenUrl = tokenUrl;
        this.headersBuilder = headersBuilder;
    }

    @Override
    public Token getAccessToken(String email, String password) {
        HttpHeaders headers = headersBuilder.getHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + tokenUrl)
                .queryParam("grant_type", "password")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("email", email)
                .queryParam("password", password);
        String uri = builder.build().toUriString();
        try {
            ResponseEntity<Token> response = template.exchange(uri, HttpMethod.POST, new HttpEntity<>("", headers), Token.class);
            Token token = response.getBody();
            LOGGER.info("Token: {}", token);
            return token;
        } catch (RuntimeException ex) {
            LOGGER.error("Could not get token.", ex);
        }
        return null;
    }

    @Override
    public Token refreshToken(Token token) {
        return null;
    }
}
