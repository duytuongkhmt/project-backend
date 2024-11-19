package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Account;
import project.model.Follower;
import project.model.Profile;
import project.repository.FollowerRepository;
import project.repository.ProfileRepository;
import project.repository.UserRepository;
import project.util.AuthUtils;

import java.time.LocalDate;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;
    private final ProfileRepository profileRepository;

    public Account findByUsername(String username) {
        return userRepository.findByUsername(username);
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

    public void addFriend(String userId, String friendId) {
        Account user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Account friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));

        user.getFriends().add(friend);
        friend.getFriends().add(user);

        userRepository.save(user);
        userRepository.save(friend);
    }

    public void followUser(String userId, String followerId) {
        Account user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Account follower = userRepository.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));

        Follower followerEntity = new Follower();
        followerEntity.setUser(user);
        followerEntity.setFollower(follower);

        followerRepository.save(followerEntity);
    }

    public void unfollowUser(String userId, String followerId) {
        followerRepository.deleteByUserIdAndFollowerId(userId, followerId);
    }

    public void removeFriend(String userId, String friendId) {
        String userName = AuthUtils.getCurrentUsername();

        Account user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Account friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        userRepository.save(user);
        userRepository.save(friend);
    }

    public Profile getProfile(){
        String userName = AuthUtils.getCurrentUsername();
        Account user= userRepository.findByUsername(userName);
        return user.getProfile();
    }
}
