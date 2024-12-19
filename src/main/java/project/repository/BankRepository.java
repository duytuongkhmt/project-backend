package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Bank;


@Repository
public interface BankRepository extends CrudRepository<Bank, String>, JpaSpecificationExecutor<Bank> {


}
