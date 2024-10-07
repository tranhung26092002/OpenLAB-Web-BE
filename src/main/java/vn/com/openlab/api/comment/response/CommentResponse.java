package vn.com.openlab.api.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.com.openlab.api.comment.model.Comment;
import vn.com.openlab.api.user.response.UserResponse;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    @JsonProperty("content")
    private String content;

    // user's infomation
    @JsonProperty("user")
    private UserResponse userResponse;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static CommentResponse fromComment(Comment comment) {
        return CommentResponse.builder()
                .content(comment.getContent())
                .userResponse(UserResponse.fromUser(comment.getUser()))
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

}
