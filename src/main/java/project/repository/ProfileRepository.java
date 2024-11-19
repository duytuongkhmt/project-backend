package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Profile;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, String>, JpaSpecificationExecutor<Profile> {

    Profile findByUserId(String id);
}
