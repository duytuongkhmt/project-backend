package project.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.RegisterBusiness;
import project.payload.request.auth.RegisterRequest;
import project.payload.response.ResponseObject;


@RestController
@RequestMapping("/api/v1/auth/register")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterBusiness registerBusiness;
    @PostMapping
    public ResponseEntity<ResponseObject> register(@Valid @RequestBody RegisterRequest registerRequest) {

        return new ResponseEntity<>(ResponseObject.ok(registerBusiness.register(registerRequest)), HttpStatus.OK);
    }

    @GetMapping(path = "/verify")
    public ResponseEntity<ResponseObject> confirm(@RequestParam("code") String token) {
        return ResponseEntity.ok(ResponseObject.ok(registerBusiness.confirmToken(token)));
    }

}
