package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Follower;

@Repository
public interface FollowerRepository extends CrudRepository<Follower, String>, JpaSpecificationExecutor<Follower> {
    void deleteByUserIdAndFollowerId(String userId,String followerId);

}
