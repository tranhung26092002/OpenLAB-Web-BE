package vn.com.openlab.api.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import vn.com.openlab.api.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUserIdAndProductId(@Param("userId") Long userId,
                                           @Param("productId") Long productId);

    List<Comment> findByProductId(@Param("productId") Long productId);

}
