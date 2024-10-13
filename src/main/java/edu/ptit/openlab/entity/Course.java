package edu.ptit.openlab.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course")
public class Course extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @Column(name = "sub_id", nullable = false, unique = true)
    private String subId;

    @Column(name = "name_course", nullable = false)
    private String nameCourse;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "type_course", length = 100, nullable = false)
    private String typeCourse;

    @Column(name = "description", columnDefinition = "LONGTEXT")  // Hỗ trợ lưu trữ văn bản dài
    private String description;

    @Column(name = "original_price", nullable = false)
    private Double originalPrice;

    @Column(name = "created_by", length = 100, nullable = false)  // Cần biết ai tạo khoá học
    private String createdBy;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.REMOVE)
    private List<User> users;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses", cascade = CascadeType.REMOVE)
    private List<Product> products;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;
}
