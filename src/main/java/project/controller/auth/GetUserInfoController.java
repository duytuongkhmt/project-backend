package project.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.business.AuthenticateBusiness;
import project.payload.response.ResponseObject;
import project.resource.UsersResource;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/info")
@RequiredArgsConstructor
public class GetUserInfoController {
    private AuthenticateBusiness authenticateBusiness;

    @GetMapping("/user")
    public ResponseEntity<ResponseObject> authenticateUser() {
        UsersResource result = authenticateBusiness.getUserInfo();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }
}
