package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.mapper.UserMapper;
import project.model.entity.Account;
import project.model.entity.Friendship;
import project.model.entity.Profile;
import project.resource.FriendshipResource;
import project.resource.ProfileResource;
import project.service.FriendshipService;
import project.service.ProfileService;
import project.service.UserService;
import project.util.AuthUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendBusiness {

    private final ProfileService profileService;
    private final UserService userService;
    private final FriendshipService friendshipService;


    public FriendshipResource checkStatusFriend(String friendId) {
        String userName = AuthUtils.getCurrentUsername();
        Account sender = userService.findByUsername(userName);
        Account receiver = profileService.getProfileById(friendId).getUser();
        FriendshipResource friendshipResource = new FriendshipResource();
        Friendship friendship1 = friendshipService.findBySenderAndReceiver(sender, receiver);
        Friendship friendship2 = friendshipService.findBySenderAndReceiver(receiver, sender);
        if (friendship1 != null) {
            friendshipResource.setStatus(friendship1.getStatus());
            friendshipResource.setRequester(friendship1.getSender().getProfile().getId());
        }
        if (friendship2 != null) {
            friendshipResource.setStatus(friendship2.getStatus());
            friendshipResource.setRequester(friendship2.getSender().getProfile().getId());
        }
        return friendshipResource;
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
        Friendship friendship = friendshipService.findBySenderAndReceiver(receiver, sender);
        if (friendship == null) {
            return;
        }
        friendship.setStatus(Friendship.STATUS.ACCEPTED);
        friendshipService.save(friendship);
    }

    public void unfriend(String friendId) {
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

    public List<ProfileResource> getSuggestFriend() {
        Account myAccount = getMyAccount();
        Profile myProfile = myAccount.getProfile();
        List<Profile> profiles = profileService.getAllActiveProfileExceptionMe(myProfile.getId());
        List<Profile> getRequestProfiles = profiles.stream().filter(profile -> {
            Friendship friendship1 = friendshipService.findBySenderAndReceiver(profile.getUser(), myAccount);
            Friendship friendship2 = friendshipService.findBySenderAndReceiver(myAccount, profile.getUser());
            return friendship1 == null && friendship2 == null;
        }).toList();
        return getRequestProfiles.stream().map(UserMapper::map).toList();
    }

    public List<ProfileResource> getPendingFriend() {
        Account myAccount = getMyAccount();
        Profile myProfile = myAccount.getProfile();
        List<Profile> profiles = profileService.getAllActiveProfileExceptionMe(myProfile.getId());
        List<Profile> getRequestProfiles = profiles.stream().filter(profile -> {
            Friendship friendship = friendshipService.findBySenderAndReceiver(profile.getUser(), myAccount);
            if (friendship != null) {
                return Friendship.STATUS.PENDING.equals(friendship.getStatus());
            }
            return false;
        }).toList();
        return getRequestProfiles.stream().map(UserMapper::map).toList();
    }
    public List<ProfileResource> getFriend() {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);

        List<Account> friends = friendshipService.getFriends(account);
        List<Profile> friendProfiles = friends.stream().map(Account::getProfile).toList();
        return friendProfiles.stream().map(UserMapper::map).toList();
    }

    public List<ProfileResource> getFriendsByProfileId(String id) {
        Profile profile = profileService.getProfileById(id);
        Account account = profile.getUser();
        List<Account> friends = friendshipService.getFriends(account);
        List<Profile> friendProfiles = friends.stream().map(Account::getProfile).toList();
        return friendProfiles.stream().map(UserMapper::map).toList();
    }
    private Account getMyAccount() {
        String userName = AuthUtils.getCurrentUsername();
        return userService.findByUsername(userName);
    }

}
