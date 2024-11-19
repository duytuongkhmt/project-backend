package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.model.Order;
import project.model.Post;
import project.model.PostMedia;
import project.payload.request.user.PostRequest;
import project.repository.PostMediaRepository;
import project.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;
import static project.repository.spec.PostSpecification.statusNotDelete;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMediaRepository mediaRepository;


    public Page<Post> getByFilter(PostRequest request, PageRequest pageRequest) {
        return postRepository.findAll(where(statusNotDelete()),pageRequest);
    }

    public Post getById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

    public Post save(Post newPost) {
        // Create Post entity
        Post post = new Post();
        BeanUtils.copyProperties(newPost, post);
        List<PostMedia> postMediaList = new ArrayList<>();
        newPost.getMediaList().forEach(media -> {
            PostMedia postMedia = new PostMedia();
            BeanUtils.copyProperties(media, postMedia);
            postMediaList.add(postMedia);

        });
        post.setMediaList(postMediaList);

        return postRepository.save(post);

    }

    public void delete(String id) {
        // Create Post entity
        Post post = getById(id);
        post.setStatus(Post.STATUS.DELETE);
        postRepository.save(post);
    }


    public Post findById(String id) {
            return postRepository.findByIdIs(id);
    }
}
