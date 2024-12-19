package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.Follow;

import java.util.List;

@Repository
public interface FollowRepository extends CrudRepository<Follow, String>, JpaSpecificationExecutor<Follow> {
    void deleteByUserIdAndFollowerId(String userId,String followerId);
    List<Follow> findByFollowerId(String followerId);

}
