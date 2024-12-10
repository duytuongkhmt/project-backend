package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.Account;
import project.model.Friendship;
import project.repository.FriendshipRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public List<Account> getFriends(Account account) {
        List<Account> friend1 = friendshipRepository.findAllBySender(account)
                .stream()
                .filter(friendship ->
                        Friendship.STATUS.ACCEPTED.equals(friendship.getStatus()))
                .map(Friendship::getReceiver)
                .collect(Collectors.toList());


        List<Account> friend2 = friendshipRepository.findAllByReceiver(account)
                .stream()
                .filter(friendship ->
                        Friendship.STATUS.ACCEPTED.equals(friendship.getStatus()))
                .map(Friendship::getSender)
                .toList();
        friend1.addAll(friend2);
        return friend1;
    }

    public void save(Friendship friendship) {
        friendshipRepository.save(friendship);
    }

    public Friendship findBySenderAndReceiver(Account sender, Account receiver) {
        return friendshipRepository.findAllBySenderAndReceiver(sender, receiver);
    }

    public void delete(Friendship friendship) {
        friendshipRepository.delete(friendship);
    }
}
