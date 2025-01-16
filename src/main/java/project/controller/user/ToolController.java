package project.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.business.FriendBusiness;
import project.business.ProfileBusiness;
import project.business.UserBusiness;
import project.model.entity.Account;
import project.payload.response.ResponseObject;
import project.resource.ProfileResource;
import project.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tool")
@RequiredArgsConstructor
public class ToolController {
    private final UserBusiness userBusiness;
    private final UserService userService;
    private final FriendBusiness friendBusiness;

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
        List<ProfileResource> result = friendBusiness.getRequestAddFriends();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

}
