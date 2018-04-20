package ninja.couto.sample.springboot.vuetify.www.service;

import ninja.couto.sample.springboot.vuetify.www.entities.Comment;
import ninja.couto.sample.springboot.vuetify.www.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public void comment(Comment comment) {
        commentRepository.save(comment);
    }

    public boolean deleteComment(Long id) {
        commentRepository.deleteById(id);
        return true;
    }
}
