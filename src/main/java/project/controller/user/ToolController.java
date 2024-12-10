package project.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.business.ProfileBusiness;
import project.business.UserBusiness;
import project.model.Account;
import project.payload.response.ResponseObject;
import project.resource.ProfileResource;
import project.resource.UserResource;
import project.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tool")
@RequiredArgsConstructor
public class ToolController {
    private final ProfileBusiness profileBusiness;
    private final UserBusiness userBusiness;
    private final UserService userService;



    @GetMapping("/account")
    public ResponseEntity<ResponseObject> getAllAccount() {
        List<Account> result = userService.findAll();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponseObject> getAllProfile() {
        List<ProfileResource> result = userBusiness.getAllProfile();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @GetMapping("/pending-profile")
    public ResponseEntity<ResponseObject> getPending() {
        List<ProfileResource> result = userBusiness.getRequestAddFriends();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }




}
