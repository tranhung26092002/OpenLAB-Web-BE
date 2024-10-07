package vn.com.openlab.api.category.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category(){

    }
}
