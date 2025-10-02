package vn.iotstar.repository;
import vn.iotstar.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByPriceAsc();
    
    List<Product> findDistinctByCategories_Id(Long categoryId);
}