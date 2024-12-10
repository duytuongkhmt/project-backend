package project.service;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import project.model.Account;
import project.model.Follow;
import project.model.Friendship;
import project.model.Profile;
import project.repository.FollowRepository;
import project.repository.FriendshipRepository;
import project.repository.ProfileRepository;
import project.repository.UserRepository;
import project.util.AuthUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final ProfileRepository profileRepository;
    private final FriendshipRepository friendshipRepository;

    public Account findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Account> findAll() {
        return userRepository.findAllByIsEmailVerifiedIs(true);
    }

    public Account findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    public void save(Account user) {
        userRepository.save(user);
    }

    public Account findByConfirmationToken(String token) {
        return userRepository.findByConfirmationToken(token);
    }

    public void enableUser(String email) {
        Account user = userRepository.findByEmail(email);
        user.setIsEmailVerified(true);
        userRepository.save(user);
    }

    public void setConfirmedAt(String token) {
        Account user = userRepository.findByConfirmationToken(token);
        user.setConfirmedAt(LocalDate.now());
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByMobile(String mobile) {
        return userRepository.existsByMobile(mobile);
    }

//    public void addFriend(String userId, String friendId) {
//        Friendship friendship = new Friendship();
//        friendship.setUserId(userId);
//        friendship.setFriendId(friendId);
//        friendship.setStatus(Friendship.STATUS.PENDING);
//        friendshipRepository.save(friendship);
//    }
//
//    public void followUser(String userId, String followerId) {
//        Account user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Account follower = userRepository.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));
//
//        Follow followerEntity = new Follow();
//        followerEntity.setUser(user);
//        followerEntity.setFollower(follower);
//
//        followRepository.save(followerEntity);
//    }
//
//    public void unfollowUser(String userId, String followerId) {
//        followRepository.deleteByUserIdAndFollowerId(userId, followerId);
//    }
//
//    public void removeFriend(String userId, String friendId) {
//        String userName = AuthUtils.getCurrentUsername();
//
//        Account user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Account friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));
//
//        user.getFriends().remove(friend);
//        friend.getFriends().remove(user);
//
//        userRepository.save(user);
//        userRepository.save(friend);
//    }

    public Profile getProfile() {
        String userName = AuthUtils.getCurrentUsername();
        Account user = userRepository.findByUsername(userName);
        return user.getProfile();
    }

//    public void sendFriendRequest(Account user, Account friend) {
//
//
//        Friendship friendship = friendshipRepository.findByUserAndFriend(user, friend);
//        if (friendship != null) {
//            friendship.setStatus(Friendship.STATUS.PENDING);
//            return;
//        }
//        friendship.setUser(user);
//        friendship.setFriend(friend);
//        friendship.setStatus("pending");
//        friendshipRepository.save(friendship);
//    }
//
//    public List<Friendship> getPendingRequests(String userId) {
//        return friendshipRepository.findAllByUserAndStatusIsNull(userId, "pending");
//    }
//
//    public void acceptFriendRequest(String friendshipId) {
//        Friendship friendship = friendshipRepository.findById(friendshipId).orElseThrow();
//        friendship.setStatus("accepted");
//        friendshipRepository.save(friendship);
//    }
}
