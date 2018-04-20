package ninja.couto.sample.springboot.vuetify.www.controllers;

import ninja.couto.sample.springboot.vuetify.www.config.CustomUserDetails;
import ninja.couto.sample.springboot.vuetify.www.entities.Comment;
import ninja.couto.sample.springboot.vuetify.www.entities.Post;
import ninja.couto.sample.springboot.vuetify.www.entities.User;
import ninja.couto.sample.springboot.vuetify.www.service.CommentService;
import ninja.couto.sample.springboot.vuetify.www.service.PostService;
import ninja.couto.sample.springboot.vuetify.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Post> posts(){
        return postService.getAllPosts();
    }

    @Secured({"ROLE_WRITER"})
    @PostMapping
    public String publishPost(@RequestBody Post post){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(post.getDateCreated() == null)
            post.setDateCreated(new Date());
        post.setCreator(userService.getUser(userDetails.getUsername()));
        postService.insert(post);
        return "Post was published";
    }

    @GetMapping(value="/{id}")
    public Post getPostById(@PathVariable Long id){
        return postService.getPost(id);
    }

    @GetMapping(value="/posts/byUsers/{username}")
    public List<Post> postsByUser(@PathVariable String username){
        return postService.findByUser(userService.getUser(username));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{id}")
    public boolean deletePost(@PathVariable Long id){
        return postService.deletePost(id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{postId}/comments")
    public boolean deleteComment(@PathVariable Long postId){
        return commentService.deleteComment(postId);
    }

    @GetMapping(value = "/{postId}/comments")
    public List<Comment> getComments(@PathVariable Long postId){
        return commentService.getComments(postId);
    }

    @Secured("ROLE_USER")
    @PostMapping(value = "/{postId}/comments")
    public boolean postComment(@PathVariable Long postId, @RequestBody String comment){
        Post post = postService.find(postId);
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User creator = userService.getUser(userDetails.getUsername());
        if(post == null || creator == null)
            return false;

        commentService.comment(new Comment(comment,post,creator));
        return true;
    }

}
