//package vn.com.openlab.repository;
//
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import vn.com.openlab.api.product.model.Product;
//
//public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
//    Page<Product> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
//}

