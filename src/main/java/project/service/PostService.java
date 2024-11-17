package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.model.Post;
import project.model.PostMedia;
import project.payload.request.user.PostRequest;
import project.repository.PostMediaRepository;
import project.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMediaRepository mediaRepository;


    public List<Post> getByFilter(PageRequest pageRequest) {
        return postRepository.findAll();
    }


    public Post getById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

    public void save(PostRequest request) {
        // Create Post entity
        Post post = new Post();
        BeanUtils.copyProperties(request, post);
        List<PostMedia> postMediaList = new ArrayList<>();
        request.getMedia().forEach(media -> {
            PostMedia postMedia = new PostMedia();
            BeanUtils.copyProperties(media, postMedia);
            postMediaList.add(postMedia);

        });
        post.setMediaList(postMediaList);

        postRepository.save(post);

    }

    public void delete(String id) {
        // Create Post entity
        Post post = getById(id);
        post.setStatus(Post.STATUS.DELETE);
        postRepository.save(post);
    }


}
