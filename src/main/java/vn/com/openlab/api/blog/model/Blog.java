package vn.com.openlab.api.blog.model;

import jakarta.persistence.*;
import lombok.*;
import vn.com.openlab.helper.base.model.BaseEntity;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blogs")
public class Blog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "thumbnail_url", length = 300)
    private String thumbnailUrl;
}
