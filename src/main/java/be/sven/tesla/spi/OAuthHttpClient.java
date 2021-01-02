package be.sven.tesla.spi;

import be.sven.tesla.model.Token;

public interface OAuthHttpClient {

    Token getAccessToken(String email, String password);
    Token refreshToken(Token token);
}
