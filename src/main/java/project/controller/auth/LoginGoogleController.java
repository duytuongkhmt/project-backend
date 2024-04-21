package project.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.AuthenticateBusiness;
import project.payload.response.ResponseObject;
import project.service.GoogleService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;


@RestController
@RequestMapping("/api/v1/auth/login/google")
@RequiredArgsConstructor
@Slf4j
public class LoginGoogleController {
    private final AuthenticateBusiness authenticateBusiness;
    private final GoogleService googleService;
    @GetMapping("verify")
    public ResponseEntity<ResponseObject> getToken(String code, String redirectUri) {
        String result = authenticateBusiness.authByGoogle(code, redirectUri);

        return ResponseEntity.ok(ResponseObject.ok(result));
    }
    @GetMapping(value = "/url", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseObject> getGoogleUrl(String redirectUri) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("scope", googleService.getScope());
        requestParams.put("response_type", "code");
        requestParams.put("redirect_uri", redirectUri);
        requestParams.put("client_id", googleService.getClientId());

        String encodedURL = requestParams.keySet().stream()
                .map(key -> key + "=" + encodeValue(requestParams.get(key)))
                .collect(joining("&", googleService.getAuthUrl() + "?", ""));
        return new ResponseEntity<>(ResponseObject.ok(encodedURL), HttpStatus.OK);
    }

    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }
}
