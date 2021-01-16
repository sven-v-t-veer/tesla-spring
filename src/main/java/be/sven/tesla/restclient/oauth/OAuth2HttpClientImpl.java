package be.sven.tesla.restclient.oauth;

import be.sven.tesla.exception.AuthenticationException;
import be.sven.tesla.core.Token;
import be.sven.tesla.restclient.util.HttpHeadersBuilder;
import be.sven.tesla.restclient.OAuthHttpClient;
import be.sven.tesla.restclient.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

@Component
public class OAuth2HttpClientImpl implements OAuthHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2HttpClientImpl.class);
    private static final long TIME_TO_REFRESH = 3801600;

    private final String clientId;
    private final String clientSecret;
    private final String baseUrl;
    private final String tokenUrl;
    private final RestTemplate template;
    private final HttpHeadersBuilder headersBuilder;
    private final RedisService tokenService;

    @Autowired
    public OAuth2HttpClientImpl(RestTemplate template, @Value("${com.tesla.clientId}") String clientId,
                                @Value("${com.tesla.clientSecret}") String clientSecret, @Value("${com.tesla.baseUrl}") String baseUrl,
                                @Value("${com.tesla.tokenUrl}") String tokenUrl, HttpHeadersBuilder headersBuilder,
                                RedisService tokenService) {
        this.template = template;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.baseUrl = baseUrl;
        this.tokenUrl = tokenUrl;
        this.headersBuilder = headersBuilder;
        this.tokenService = tokenService;
    }

    @Override
    public Token getAccessToken(String email, String password) throws AuthenticationException{
        LOGGER.info("Getting oauth token for {}.", email);
        Token token = getAccessToken(email.hashCode());
        if (token != null) {
            return token;
        }
        LOGGER.info("Token not found in redis, getting a new token.");
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("email", email);
        params.put("password", password);
        token = getToken(params);
        if (token == null) {
            throw new AuthenticationException("Authentication token was null.");
        }
        token.setId(email.hashCode());
        return tokenService.saveToken(token);
    }

    @Override
    public Token getAccessToken(int id) throws AuthenticationException{
        LOGGER.info("Getting oauth token for {}.", id);
        return getToken(id);
    }

    @Override
    public Token refreshToken(Token token) throws AuthenticationException{
        Integer id = token.getId();
        if (id == null) {
            //TODO throw exception;
            return null;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("refresh_token", token.getRefreshToken());
        token = getToken(params);
        if (token == null) {
            return null;
        }
        token.setId(id);
        tokenService.saveToken(token);
        return token;
    }

    private Token getToken(int id) throws AuthenticationException{
        Token token = null;
        Optional<Token> optional = tokenService.getTokenFor(id);
        if (optional.isPresent()) {
            LOGGER.info("Acquired token from redis.");
            token = optional.get();
            Instant now = Instant.now();
            Instant created = Instant.ofEpochSecond(token.getCreatedAt());
            Duration expires = Duration.ofSeconds(TIME_TO_REFRESH);
            if (now.isBefore(created.plus(expires))) {
                LOGGER.info("The acquired token is not expired");
                return token;
            } else {
                LOGGER.info("Token is ready for refresh.");
                return refreshToken(token);
            }
        }
        return null;
    }

    private Token getToken(HashMap<String, String> params) throws AuthenticationException{
        Token token;
        HttpHeaders headers = headersBuilder.getHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + tokenUrl);
        params.forEach(builder::queryParam);
        String uri = builder.build().toUriString();
        ResponseEntity<Token> response = template.exchange(uri, HttpMethod.POST, new HttpEntity<>("", headers), Token.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        throw new AuthenticationException("Unable to get access token. Authentication returned response code " + response.getStatusCode() + ".");
    }
}
