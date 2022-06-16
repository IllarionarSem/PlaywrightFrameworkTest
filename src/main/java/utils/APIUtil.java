package utils;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

import java.util.Map;
import java.util.Objects;

public class APIUtil {

    public static final Map<String, String> defaultHeaders = Map.of("Content-Type", "application/json", "Accept", "application/json");

    private final APIRequestContext context;

    public APIUtil(Playwright playwright) {
        context = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(Config.getValue("baseAPIUrl"))
                .setExtraHTTPHeaders(defaultHeaders));
    }

    public APIUtil(APIUtilBuilder builder) {
        context = builder.getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(builder.getBaseUrl())
                .setExtraHTTPHeaders(builder.getHeaders()));
    }

    public APIResponse post(APIEndpoint api, RequestOptions requestOptions) {
        return context.post(api.getText(), requestOptions);
    }

    public APIResponse put(APIEndpoint api, RequestOptions requestOptions) {
        return context.put(api.getText(), requestOptions);
    }

    public APIResponse put(APIEndpoint api, String body) {
        return put(api, RequestOptions.create().setData(body));
    }

    public APIResponse patch(APIEndpoint api, RequestOptions requestOptions) {
        return context.patch(api.getText(), requestOptions);
    }

    public APIResponse patch(APIEndpoint api, String body) {
        return patch(api, RequestOptions.create().setData(body));
    }

    public APIResponse post(APIEndpoint api, String body) {
        return post(api, RequestOptions.create().setData(body));
    }

    public APIResponse get(APIEndpoint api) {
        return context.get(api.getText());
    }

    public APIResponse delete(APIEndpoint api) {
        return context.delete(api.getText());
    }

    public void dispose() {
        if (Objects.nonNull(context)) {
            context.dispose();
        }
    }
}
