package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Account;

@Repository
public interface UserRepository extends CrudRepository<Account, String>, JpaSpecificationExecutor<Account> {
    Account findByUsername(String username);

    Account findByEmail(String username);

    Account findByConfirmationToken(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByMobile(String mobile);

}
