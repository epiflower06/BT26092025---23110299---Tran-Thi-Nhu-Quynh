package vn.iotstar.service;
import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProductsSortedByPrice() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Product saveProduct(Product p) { return productRepository.save(p); }
    public void deleteProduct(Long id) { productRepository.deleteById(id); }
}
