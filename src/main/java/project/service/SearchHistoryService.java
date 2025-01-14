package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.entity.SearchHistory;
import project.repository.SearchHistoryRepository;

import static org.springframework.data.jpa.domain.Specification.where;
import static project.repository.spec.SearchHistorySpecification.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    public List<SearchHistory> getHistories(String profileId) {
        return searchHistoryRepository.findAll(where(userIdIs(profileId).and(notDelete())));
    }

    public void save(SearchHistory searchHistory) {
        searchHistoryRepository.save(searchHistory);
    }
}
