package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Account;

@Repository
public interface UsersRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    Account findByEmail(String username);

    Account findByConfirmationToken(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByMobile(String mobile);

}
