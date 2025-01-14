package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.SearchHistory;

import java.util.List;


@Repository
public interface SearchHistoryRepository  extends CrudRepository<SearchHistory, String>, JpaSpecificationExecutor<SearchHistory> {

    List<SearchHistory> findByUserId(String id);
}
