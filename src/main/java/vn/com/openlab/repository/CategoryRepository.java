package vn.com.openlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.com.openlab.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
