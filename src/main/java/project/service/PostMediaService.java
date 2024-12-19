package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.entity.PostMedia;
import project.repository.PostMediaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostMediaService {
    private final PostMediaRepository postMediaRepository;

    public PostMedia save(PostMedia post) {
        return postMediaRepository.save(post);
    }

    public List<PostMedia> saveAll(List<PostMedia> medias) {
        return postMediaRepository.saveAll(medias);
    }

    public List<PostMedia> getByPostId(String postId) {
        return postMediaRepository.findByPostIdIs(postId);
    }

    public void deleteByPostId(String postId) {
        postMediaRepository.deleteByPostIdIs(postId);
    }
}
