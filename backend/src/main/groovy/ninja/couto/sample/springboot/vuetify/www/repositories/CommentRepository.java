package ninja.couto.sample.springboot.vuetify.www.repositories;

import ninja.couto.sample.springboot.vuetify.www.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(Long postId);
}
