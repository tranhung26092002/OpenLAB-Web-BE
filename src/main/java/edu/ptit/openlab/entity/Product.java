package edu.ptit.openlab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "sub_id", nullable = false, unique = true)
    private String subId;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "name_product")
    private String nameProduct;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "type_product", length = 100)
    private String typeProduct;

    @Column(name = "created_by")  // Cần biết ai tạo khoá học
    private String createdBy;

    @JsonIgnore
    @ManyToMany(mappedBy = "products", cascade = CascadeType.REMOVE)
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "product_course", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses = new ArrayList<>();
}
