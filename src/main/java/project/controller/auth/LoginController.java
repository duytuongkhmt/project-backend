package project.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.business.AuthenticateBusiness;
import project.model.entity.Account;
import project.payload.request.auth.LoginRequest;
import project.payload.response.ResponseObject;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticateBusiness authenticateBusiness;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Account user = authenticateBusiness.getUser(loginRequest.getUsername());
        if (user == null) {
            return ResponseEntity.ok(ResponseObject.error("Username or password is invalid", HttpStatus.BAD_REQUEST));
        }
        if (!user.getIsEmailVerified()) {
            return ResponseEntity.ok(ResponseObject.error("Email not verified", HttpStatus.BAD_REQUEST));
        }

        try {
            String token = authenticateBusiness.auth(loginRequest, user);
            return ResponseEntity.ok(ResponseObject.ok(token));
        } catch (Exception e) {
            log.error("Login error: ", e);
            return ResponseEntity.ok(ResponseObject.error(e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
}
