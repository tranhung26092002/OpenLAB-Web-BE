package vn.com.openlab.model;

import jakarta.persistence.*;
import lombok.*;
import vn.com.openlab.helper.base.model.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lesson")
public class Lesson extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;  // Đổi thành String vì URL có thể gặp vấn đề với một số trường hợp

    @Column(name = "document_url", nullable = false)
    private String documentUrl;  // Đổi thành String vì URL có thể gặp vấn đề với một số trường hợp

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")  // Hỗ trợ lưu trữ văn bản dài
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}