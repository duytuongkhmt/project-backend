package project.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import project.common.Constant;
import project.model.entity.SearchHistory;

public class SearchHistorySpecification {
    public static Specification<SearchHistory> userIdIs(String profileId){
        if (profileId == null) {
            return null;
        }
        return ((root, query, cb) -> cb.equal(root.get(Constant.COLUMN.USER_ID), profileId));
    }

    public static Specification<SearchHistory> notDelete(){

        return ((root, query, cb) -> cb.equal(root.get(Constant.COLUMN.IS_DELETE), false));
    }

}
