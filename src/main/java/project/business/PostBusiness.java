package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import project.model.entity.Post;
import project.model.entity.PostMedia;
import project.payload.request.user.*;
import project.resource.PostResource;
import project.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostBusiness {
    private final PostService postService;

    public Page<PostResource> getPosts(PostRequest request, PageRequest pageRequest) {

        Page<Post> posts = postService.getByFilter(request, pageRequest);
        return posts.map(post -> {
            PostResource postResource = new PostResource();
            BeanUtils.copyProperties(post, postResource);
            return postResource;
        });
    }

    public PostResource getById(String id) {
        Post post = postService.findById(id);
        PostResource postResource = new PostResource();
        BeanUtils.copyProperties(post, postResource);
        return postResource;
    }

    public Page<PostResource> getPostResources(PostRequest postRequest, PageRequest pageRequest) {
        Page<Post> posts = postService.getByFilter(postRequest, pageRequest);
        return posts.map(order -> {
            PostResource postResource = new PostResource();
            BeanUtils.copyProperties(order, postResource);
            return postResource;
        });
    }

    public PostResource create(PostCreateRequest request) {
        Post post = new Post();
        BeanUtils.copyProperties(request, post);

        List<PostMedia> postMediaList = new ArrayList<>();
        request.getMediaList().forEach(media -> {
            PostMedia postMedia = new PostMedia();
            BeanUtils.copyProperties(media, postMedia);
            postMediaList.add(postMedia);

        });
        post.setMediaList(postMediaList);
        post = postService.save(post);
        PostResource postResource = new PostResource();
        BeanUtils.copyProperties(post, postResource);
        return postResource;
    }


    public PostResource update(PostUpdateRequest request) {
        Post post = postService.findById(request.getId());
        BeanUtils.copyProperties(request, post);

        post = postService.save(post);
        PostResource postResource = new PostResource();
        BeanUtils.copyProperties(post, postResource);
        return postResource;

    }

    public void deleteById(String id) {
        postService.delete(id);
    }
}
