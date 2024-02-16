package cnu.likelion.board.post.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {

    private final Map<Long, Post> posts = new HashMap<>();
    private Long sequence = 1L;

    public Post save(Post post) {
        post.setId(sequence++);
        posts.put(post.getId(), post);
        return post;
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public void delete(Post post) {
        posts.remove(post.getId());
    }

    // for test
    public void clear() {
        this.posts.clear();
    }
}
