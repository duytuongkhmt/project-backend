package project.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.model.entity.Comment;
import project.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment getCommentById(String id){
        return commentRepository.findById(id).orElse(null);
    }
    public List<Comment> getCommentPyPostId(String id){
        return commentRepository.findByPostId(id);
    }

    public void save(Comment comment){
        commentRepository.save(comment);
    }
    public void saveAll(List<Comment> comments){
        commentRepository.saveAll(comments);
    }

    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
    public void deleteByPostId(String id) {
        commentRepository.deleteByPostId(id);
    }
}
