package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.Account;
import project.model.entity.Friendship;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {
    List<Friendship> findAllBySender(Account user);
    List<Friendship> findAllByReceiver(Account user);
    Friendship findAllBySenderAndReceiver(Account user, Account receiver);
}
