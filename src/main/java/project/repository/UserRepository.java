package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.Account;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<Account, String>, JpaSpecificationExecutor<Account> {
    Account findByUsername(String username);

    Account findByEmail(String username);

    Account findByConfirmationToken(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByMobile(String mobile);
    List<Account> findAllByIsEmailVerifiedIs(Boolean isEmailVerified);

}
