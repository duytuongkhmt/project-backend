package project.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.FriendBusiness;
import project.payload.response.ResponseObject;
import project.resource.FriendshipResource;
import project.resource.ProfileResource;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friend")
@RequiredArgsConstructor
public class FriendShipController {
    private final FriendBusiness friendBusiness;

    @GetMapping
    public ResponseEntity<ResponseObject> getFriends() {
        List<ProfileResource> result = friendBusiness.getFriend();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<ResponseObject> getFriendsByProfileId(@PathVariable String id) {
        List<ProfileResource> result = friendBusiness.getFriendsByProfileId(id);
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @PutMapping
    public ResponseEntity<ResponseObject> addFriend( @RequestParam String id) {
        friendBusiness.sendRequestFriend(id);
        return ResponseEntity.ok(ResponseObject.ok("ok"));
    }


    @PutMapping("/accept")
    public ResponseEntity<ResponseObject> acceptFriend(@RequestParam String id) {
        friendBusiness.acceptRequestFriend(id);
        return ResponseEntity.ok(ResponseObject.ok("ok"));
    }

    @PutMapping("unfriend")
    public ResponseEntity<ResponseObject> unfriend( @RequestParam String id) {
        friendBusiness.unfriend(id);
        return ResponseEntity.ok(ResponseObject.ok("ok"));
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<ResponseObject> getFriends(@PathVariable String id) {
        FriendshipResource result = friendBusiness.checkStatusFriend(id);
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @GetMapping("/suggest-friend")
    public ResponseEntity<ResponseObject> getSuggestFriend() {
        List<ProfileResource> result = friendBusiness.getSuggestFriend();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @GetMapping("/pending-friend")
    public ResponseEntity<ResponseObject> getPendingFriend() {
        List<ProfileResource> result = friendBusiness.getPendingFriend();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

}
