package utils;

import com.microsoft.playwright.Playwright;
import lombok.Getter;
import model.Token;

import java.util.HashMap;
import java.util.Map;

@Getter
public class APIUtilBuilder {

    private final Playwright playwright;
    private String baseUrl;
    private final Map<String, String> headers = new HashMap<>();

    public APIUtilBuilder(Playwright playwright) {
        this.playwright = playwright;
    }

    public APIUtilBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public APIUtilBuilder addHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public APIUtilBuilder putHeaderValue(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public APIUtilBuilder setToken(String key, Token token) {
        this.headers.put(key, String.format("token=%s", token.getToken()));
        return this;
    }

    public APIUtil build() {
        return new APIUtil(this);
    }
}
