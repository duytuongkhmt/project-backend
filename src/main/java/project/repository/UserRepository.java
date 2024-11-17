package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Account;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Account, String> {
    Account findByUsername(String username);

    Account findByEmail(String username);

    Account findByConfirmationToken(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByMobile(String mobile);

}
