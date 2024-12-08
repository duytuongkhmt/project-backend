package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.model.Profile;
import project.service.ProfileService;
import project.service.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendBusiness {

    private final ProfileService profileService;
    private final UserService userService;


    public void addFriend(String id) {

    }

    public void unFriend(String id) {

    }

    public void follow(String id) {

    }

    public void unFollow(String id) {
    }

    public void getFriendSuggestionList() {

    }
}
