package project.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.UserBusiness;
import project.payload.response.ResponseObject;
import project.resource.FriendshipResource;
import project.resource.ProfileResource;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friend")
@RequiredArgsConstructor
public class FriendShipController {
    private final UserBusiness userBusiness;

    @GetMapping
    public ResponseEntity<ResponseObject> getFriends() {
        List<ProfileResource> result = userBusiness.getFriend();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @PutMapping
    public ResponseEntity<ResponseObject> addFriend( @RequestParam String id) {
        userBusiness.sendRequestFriend(id);
        return ResponseEntity.ok(ResponseObject.ok("ok"));
    }


    @PutMapping("/accept")
    public ResponseEntity<ResponseObject> acceptFriend(@RequestParam String id) {
        userBusiness.acceptRequestFriend(id);
        return ResponseEntity.ok(ResponseObject.ok("ok"));
    }

    @PutMapping("unfriend")
    public ResponseEntity<ResponseObject> unfriend( @RequestParam String id) {
        userBusiness.unfriend(id);
        return ResponseEntity.ok(ResponseObject.ok("ok"));
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<ResponseObject> getFriends(@PathVariable String id) {
        FriendshipResource result = userBusiness.checkStatusFriend(id);
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @GetMapping("/suggest-friend")
    public ResponseEntity<ResponseObject> getSuggestFriend() {
        List<ProfileResource> result = userBusiness.getSuggestFriend();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @GetMapping("/pending-friend")
    public ResponseEntity<ResponseObject> getPendingFriend() {
        List<ProfileResource> result = userBusiness.getPendingFriend();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

}
