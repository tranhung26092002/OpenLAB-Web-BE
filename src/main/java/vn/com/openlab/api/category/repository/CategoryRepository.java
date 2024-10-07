package vn.com.openlab.api.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.api.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
