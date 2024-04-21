package project.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Getter
public class GoogleService {
    @Value("${google.client-id}")
    String clientId;
    @Value("${google.client-secret}")
    String clientSecret;
    @Value("${google.scope}")
    String scope;
    @Value("${google.auth-url}")
    String authUrl;
    @Value("${google.get-token-url}")
    String getTokenUrl;

    public String getAccessToken(String code, String redirectUri) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String url = "https://accounts.google.com/o/oauth2/token?" + "client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&redirect_uri=" + redirectUri
                + "&grant_type=authorization_code"
                + "&code=" + code;

        try {
            HttpResponse<JsonNode> response = Unirest.post(url).headers(headers).asJson();
            if (response.getBody() == null) {
                log.info("POST Response: {} - message: {}", response.getStatus(), response.getStatusText());
                return null;
            }
            JsonNode responseBody = response.getBody();
            return responseBody.getObject().getString("access_token");
        } catch (Exception e) {
            log.error("Service exception: ", e);
            return null;
        }

    }

    public String getEmail(String accessToken) {
        try {
            String url = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;

            HttpResponse<JsonNode> response = Unirest.get(url).asJson();

            if (response.isSuccess()) {
                JsonNode body = response.getBody();
                return body.getObject().getString("email");
            }
        } catch (Exception e) {
            log.error("getEmail error ", e);
        }
        return null;
    }

}
