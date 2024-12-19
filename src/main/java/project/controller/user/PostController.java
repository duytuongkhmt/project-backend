package project.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.business.PostBusiness;
import project.model.entity.PostMedia;
import project.payload.request.PaginateRequest;
import project.payload.request.user.*;
import project.payload.response.ResponseObject;
import project.resource.OrderResource;
import project.resource.PostResource;
import project.util.PagingUtil;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/post")
@RequiredArgsConstructor
public class PostController {
    private final PostBusiness postBusiness;

    @GetMapping
    public ResponseEntity<ResponseObject> index(PostRequest postRequest, PaginateRequest paginateRequest) {
        Sort sort = Sort.by(paginateRequest.getSort(), paginateRequest.getColumn());
        PageRequest pageRequest = PagingUtil.getPageRequest(paginateRequest.getPage(), paginateRequest.getLimit(), sort);

        Page<PostResource> postResources = postBusiness.getPosts(postRequest, pageRequest);
        ResponseObject.Meta meta = new ResponseObject.Meta();
        meta.setPage(pageRequest.getPageNumber());
        meta.setTotal(postResources.getTotalElements());
        meta.setMessage("Ok");

        ResponseObject result = new ResponseObject(postResources.getContent(), meta);
        return ResponseEntity.ok(result);
    }

    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<ResponseObject> show(@PathVariable String id) {
        PostResource postResource = postBusiness.getById(id);
        return ResponseEntity.ok(new ResponseObject(postResource));
    }

    @PostMapping(value = {"/create", "/create/"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> store(
            @RequestPart("request") String requestJson, // Nhận JSON dạng chuỗi
            @RequestPart("files") List<MultipartFile> files) {

        // Parse JSON sang PostCreateRequest
        ObjectMapper objectMapper = new ObjectMapper();
        PostCreateRequest request;
        try {
            request = objectMapper.readValue(requestJson, PostCreateRequest.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(ResponseObject.error("Invalid JSON format", HttpStatus.BAD_REQUEST));
        }

        // Kiểm tra files không rỗng
        if (files == null || files.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseObject.error("No files uploaded", HttpStatus.BAD_REQUEST));
        }

        PostResource postResource = postBusiness.create(request, files);
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

    @GetMapping({"/like/{id}","/like/{id}/"})
    public ResponseEntity<ResponseObject> like(@PathVariable String id) {
        postBusiness.like(id);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }


    @GetMapping({"/comment","/comment/"})
    public ResponseEntity<ResponseObject> comment(@RequestParam String id) {
        return ResponseEntity.ok(ResponseObject.ok(postBusiness.getCommentsByPostId(id)));
    }
    @PostMapping({"/comment","/comment/"})
    public ResponseEntity<ResponseObject> comment(@RequestBody CommentCreateRequest request){
        return ResponseEntity.ok(ResponseObject.ok(postBusiness.createComment(request)));
    }

    @PutMapping({"/comment/{id}", "/comment/{id}/"})
    public ResponseEntity<ResponseObject> updateComment(@RequestBody CommentUpdateRequest request) {
        postBusiness.updateComment(request);
        return ResponseEntity.ok(ResponseObject.ok(postBusiness.updateComment(request)));
    }

    @DeleteMapping({"/comment/delete/{id}", "/comment/delete/{id}/"})
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable String id) {
        postBusiness.deleteCommentById(id);
        return ResponseEntity.ok(ResponseObject.ok("Ok"));
    }


}
