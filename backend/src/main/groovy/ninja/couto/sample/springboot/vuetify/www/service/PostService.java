package ninja.couto.sample.springboot.vuetify.www.service;

import ninja.couto.sample.springboot.vuetify.www.entities.Post;
import ninja.couto.sample.springboot.vuetify.www.entities.User;
import ninja.couto.sample.springboot.vuetify.www.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public void insert(Post post) {
        postRepository.save(post);
    }

    public List<Post> findByUser(User user){
        return postRepository.findByCreatorId(user.getId());
    }

    public boolean deletePost(Long postId){
        Optional<Post> thePost = postRepository.findById(postId);
        if(!thePost.isPresent())
            return false;
        postRepository.delete(thePost.get());
        return true;
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).get();
    }

    public Post find(Long postId) {
        return postRepository.findById(postId).get();
    }
}
