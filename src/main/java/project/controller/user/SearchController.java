package project.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.ProfileBusiness;
import project.payload.request.PaginateRequest;
import project.payload.request.user.SearchRequest;
import project.payload.response.ResponseObject;
import project.resource.OrderResource;
import project.resource.ProfileResource;
import project.resource.SearchHistoryResource;
import project.util.PagingUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class SearchController {
    private final ProfileBusiness profileBusiness;

    @GetMapping({"/search", "/search/"})
    public ResponseEntity<ResponseObject> getProfilesByKey(SearchRequest request, PaginateRequest paginateRequest) {
        Sort sort = Sort.by(paginateRequest.getSort(), paginateRequest.getColumn());
        PageRequest pageRequest = PagingUtils.getPageRequest(paginateRequest.getPage(), paginateRequest.getLimit(), sort);
        Page<ProfileResource> profileResources = profileBusiness.search(request, pageRequest);
        Map<String, Object> meta = PagingUtils.createMeta(profileResources);
        ResponseObject result = new ResponseObject(profileResources.getContent(), meta);
        return ResponseEntity.ok(result);
    }

    @GetMapping({"/search-history", "/search-history/"})
    public ResponseEntity<ResponseObject> getHistory() {
        List<SearchHistoryResource> result = profileBusiness.getHistory();
        return ResponseEntity.ok(ResponseObject.ok(result));
    }

    @PostMapping("/search/save-history")
    public ResponseEntity<ResponseObject> saveHistory(@RequestParam String key) {
        profileBusiness.saveHistory(key);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }

    @DeleteMapping("/search/{id}")
    public ResponseEntity<ResponseObject> deleteSearchHistory(@PathVariable String id) {
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }
}
