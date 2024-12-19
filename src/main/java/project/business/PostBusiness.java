package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import project.mapper.PostMapper;
import project.model.entity.*;
import project.payload.request.user.*;
import project.resource.CommentResource;
import project.resource.PostResource;
import project.service.*;
import project.util.AuthUtils;
import project.util.FileUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostBusiness {
    private final PostService postService;
    private final PostMediaService postMediaService;
    private final CommentService commentService;
    private final UserService userService;
    private final ProfileService profileService;

    public Page<PostResource> getPosts(PostRequest request, PageRequest pageRequest) {

        Page<Post> posts = postService.getByFilter(request, pageRequest);
        Set<String> profileIds = new HashSet<>();
        posts.stream().forEach(post -> {
            if (post.getLikePeople()!=null) {
                profileIds.addAll(post.getLikePeople());
            }
        });
        List<Profile> profiles = profileService.findByIds(profileIds.stream().toList());
        Map<String, Profile> profileMap = profiles.stream()
                .collect(Collectors.toMap(Profile::getId, Function.identity()));
        return posts.map(post -> PostMapper.map(post, profileMap));
    }

    public PostResource getById(String id) {
        Post post = postService.findById(id);
        List<Profile> profiles = profileService.findByIds(post.getLikePeople());
        Map<String, Profile> profileMap = profiles.stream()
                .collect(Collectors.toMap(Profile::getId, Function.identity()));
        return PostMapper.map(post, profileMap);
    }


    public PostResource create(PostCreateRequest request, @RequestPart("files") List<MultipartFile> files) {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);
        Profile profile = account.getProfile();

        // Tạo đối tượng Post
        Post post = new Post();
        BeanUtils.copyProperties(request, post);
        post.setProfile(profile);
        post = postService.save(post);
        List<PostMedia> mediaList = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            final Post finalPost = post;
            mediaList = files.stream().map(file -> {
                String type = Objects.requireNonNull(file.getContentType()).startsWith("image") ? "image" : "video";
                String url = FileUtil.storeFile(file, type); // Lưu file
                PostMedia media = new PostMedia();
                media.setUrl(url);
                media.setType(type);
                media.setPost(finalPost);
                return media;
            }).collect(Collectors.toList());
            postMediaService.saveAll(mediaList);

        }
        post.setMediaList(mediaList);

        return PostMapper.map(post, Collections.emptyMap());
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
        commentService.deleteByPostId(id);
        postService.delete(id);
    }

    public void like(String id) {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);
        Profile profile = account.getProfile();
        Post post = postService.findById(id);
        List<String> likes = new ArrayList<>(post.getLikePeople() != null ? post.getLikePeople() : new ArrayList<>());
        if (likes.contains(profile.getId())) {
            likes.remove(profile.getId());
        } else {
            likes.add(profile.getId());
        }
        post.setLikePeople(likes);
        postService.save(post);
    }

    public void comment(CommentRequest request) {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);
        Profile profile = account.getProfile();
        Post post = postService.findById(request.getPostId());

        Comment comment = new Comment();
        BeanUtils.copyProperties(request, comment);
        comment.setProfile(profile);
        post.getComments().add(comment);
    }


    public void deleteCommentById(String id) {
        commentService.deleteById(id);
    }

    public CommentResource createComment(CommentCreateRequest request) {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);
        Profile profile = account.getProfile();
        Post post = postService.findById(request.getPostId());
        Comment comment = new Comment();
        BeanUtils.copyProperties(request, comment);
        comment.setPost(post);
        comment.setProfile(profile);
        commentService.save(comment);
        return PostMapper.map(comment);
    }

    public CommentResource updateComment(CommentUpdateRequest request) {
        Comment comment = commentService.getCommentById(request.getId());
        comment.setContent(request.getContent());
        commentService.save(comment);
        return PostMapper.map(comment);
    }

    public List<CommentResource> getCommentsByPostId(String id) {
        List<Comment> comments = commentService.getCommentPyPostId(id);
        return comments.stream().map(PostMapper::map).collect(Collectors.toList());
    }
}
