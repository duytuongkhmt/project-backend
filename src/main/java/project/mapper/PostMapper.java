package project.mapper;

import org.springframework.beans.BeanUtils;
import project.model.entity.*;
import project.resource.*;

import java.util.List;
import java.util.Map;

public class PostMapper {
    private PostMapper() {
    }

    public static PostResource map(Post post, Map<String,Profile> profileMap) {

        PostResource postResource = new PostResource();
        BeanUtils.copyProperties(post, postResource);
        List<Comment> comments = post.getComments();
        if (comments != null && !comments.isEmpty()) {
            List<CommentResource> commentResources = comments.stream()
                    .map(PostMapper::map).toList();
            postResource.setComments(commentResources);
        }
        List<MediaResource> mediaResources = post.getMediaList()
                .stream()
                .map(postMedia -> {
                    MediaResource mediaResource = new MediaResource();
                    BeanUtils.copyProperties(postMedia, mediaResource);
                    return mediaResource;
                }).toList();
        if(post.getLikePeople()!=null&&!post.getLikePeople().isEmpty()){
            List<Profile> profiles= post.getLikePeople().stream().map(profileMap::get).toList();
            postResource.setLikePeople(profiles.stream().map(UserMapper::map).toList());
        }
        postResource.setProfile(UserMapper.map(post.getProfile()));
        postResource.setMedias(mediaResources);
        return postResource;
    }

    public static CommentResource map(Comment comment) {
        CommentResource commentResource = new CommentResource();
        BeanUtils.copyProperties(comment, commentResource);
        ProfileResource profileResource = UserMapper.map(comment.getProfile());
        commentResource.setProfile(profileResource);
        return commentResource;
    }

}
