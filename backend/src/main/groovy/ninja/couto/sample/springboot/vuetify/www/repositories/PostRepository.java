package ninja.couto.sample.springboot.vuetify.www.repositories;

import ninja.couto.sample.springboot.vuetify.www.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

    List<Post> findByCreatorId(Long id);

}
