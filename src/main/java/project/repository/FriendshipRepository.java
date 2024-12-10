package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Account;
import project.model.Friendship;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {
    List<Friendship> findAllBySender(Account user);
    List<Friendship> findAllByReceiver(Account user);
    Friendship findAllBySenderAndReceiver(Account user, Account receiver);
}
