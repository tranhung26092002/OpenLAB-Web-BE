package vn.com.openlab.api.comment.service;


import vn.com.openlab.api.comment.dto.CommentDTO;
import vn.com.openlab.helper.exception.payload.DataNotFoundException;
import vn.com.openlab.api.comment.model.Comment;
import vn.com.openlab.api.comment.response.CommentResponse;

import java.util.List;

public interface CommentService {
    Comment insertComment(CommentDTO comment);

    void deleteComment(Long id);

    void updateComment(Long id, CommentDTO comment) throws DataNotFoundException;

    List<CommentResponse> getCommentByUserAndProduct(Long userId, Long productId);

    List<CommentResponse> getCommentByProduct(Long productId);
}
