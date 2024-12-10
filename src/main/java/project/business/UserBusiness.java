package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.mapper.UserMapper;
import project.model.Account;
import project.model.Friendship;
import project.model.Profile;
import project.resource.ProfileResource;
import project.resource.UserResource;
import project.service.FriendshipService;
import project.service.ProfileService;
import project.service.UserService;
import project.util.AuthUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserBusiness {
    private final UserService userService;
    private final FriendshipService friendshipService;
    private final ProfileService profileService;


    public List<ProfileResource> getFriend() {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);

        List<Account> friends = friendshipService.getFriends(account);
        List<Profile> friendProfiles = friends.stream().map(Account::getProfile).toList();
        return friendProfiles.stream().map(UserMapper::map).toList();
    }

    public List<ProfileResource> getRequestAddFriends() {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);

        List<Friendship> friendPending = account.getFriendRequestsReceived().stream().filter(friendship -> friendship.getStatus().equals("pending")).toList();
        List<String> ids = friendPending.stream().map(
                friendship -> friendship.getSender().getProfile().getId()).toList();
        List<Profile> friendProfiles = profileService.findByIds(ids);
        return friendProfiles.stream().map(UserMapper::map).toList();
    }


    public List<UserResource> getAllUser() {
        List<Account> accounts = userService.findAll();
        return accounts.stream().map(UserMapper::map).toList();
    }

    public List<ProfileResource> getAllProfile() {
        List<Profile> profiles = profileService.findAll();
        return profiles.stream().map(UserMapper::map).toList();
    }

    public void sendRequestFriend(String receiverId) {
        String userName = AuthUtils.getCurrentUsername();
        Account sender = userService.findByUsername(userName);
        Account receiver = profileService.getProfileById(receiverId).getUser();
        Friendship friendship = new Friendship();
        friendship.setSender(sender);
        friendship.setReceiver(receiver);
        friendshipService.save(friendship);
    }

    public void acceptRequestFriend(String receiverId) {
        String userName = AuthUtils.getCurrentUsername();
        Account sender = userService.findByUsername(userName);
        Account receiver = profileService.getProfileById(receiverId).getUser();
        Friendship friendship = friendshipService.findBySenderAndReceiver( receiver, sender);
        if(friendship == null) {
            return;
        }
        friendship.setStatus(Friendship.STATUS.ACCEPTED);
        friendshipService.save(friendship);
    }

    public void unfriend(String friendId){
        String userName = AuthUtils.getCurrentUsername();
        Account sender = userService.findByUsername(userName);
        Account receiver = profileService.getProfileById(friendId).getUser();
        Friendship friendship1 = friendshipService.findBySenderAndReceiver(sender, receiver);
        Friendship friendship2 = friendshipService.findBySenderAndReceiver(receiver, sender);

        if (friendship1 != null) {
            friendshipService.delete(friendship1);
        }
        if (friendship2 != null) {
            friendshipService.delete(friendship2);
        }
    }

    public String checkStatusFriend(String friendId) {
        String status = null;
        String userName = AuthUtils.getCurrentUsername();
        Account sender = userService.findByUsername(userName);
        Account receiver = profileService.getProfileById(friendId).getUser();

        Friendship friendship1 = friendshipService.findBySenderAndReceiver(sender, receiver);
        Friendship friendship2 = friendshipService.findBySenderAndReceiver(receiver, sender);
        if (friendship1 != null) {
            status=friendship1.getStatus();
        }
        if (friendship2 != null) {
            status=friendship2.getStatus();
        }
        return status;
    }
}
