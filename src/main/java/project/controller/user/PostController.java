package project.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.business.PostBusiness;
import project.payload.request.PaginateRequest;
import project.payload.request.user.*;
import project.payload.response.ResponseObject;
import project.resource.OrderResource;
import project.resource.PostResource;
import project.util.PagingUtil;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/post")
@RequiredArgsConstructor
public class PostController {
    private final PostBusiness postBusiness;

    @GetMapping
    public ResponseEntity<ResponseObject> index(PostRequest orderRequest, PaginateRequest paginateRequest) {
        Sort sort = Sort.by(paginateRequest.getSort(), paginateRequest.getColumn());
        PageRequest pageRequest = PagingUtil.getPageRequest(paginateRequest.getPage(), paginateRequest.getLimit(), sort);

        Page<PostResource> postResources = postBusiness.getPosts(orderRequest, pageRequest);
        ResponseObject.Meta meta = new ResponseObject.Meta();
        meta.setPage(pageRequest.getPageNumber());
        meta.setTotal(postResources.getTotalElements());
        meta.setMessage("Ok");

        ResponseObject result = new ResponseObject(postResources.getContent(), meta);
        return ResponseEntity.ok(new ResponseObject(result));
    }

    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<ResponseObject> show(@PathVariable String id) {
        PostResource postResource = postBusiness.getById(id);
        return ResponseEntity.ok(new ResponseObject(postResource));
    }

    @PostMapping({"/create", "/create/"})
    public ResponseEntity<ResponseObject> store(@RequestBody @Valid PostCreateRequest request) {
        PostResource postResource = postBusiness.create(request);
        return ResponseEntity.ok(ResponseObject.ok(postResource));
    }

    @PutMapping({"/update", "/update/"})
    public ResponseEntity<ResponseObject> update(@RequestBody @Valid PostUpdateRequest request) {
        return ResponseEntity.ok(ResponseObject.ok(postBusiness.update(request)));
    }


    @DeleteMapping({"/delete/{id}", "/delete/{id}/"})
    public ResponseEntity<ResponseObject> delete(@PathVariable String id) {
        postBusiness.deleteById(id);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }
}
