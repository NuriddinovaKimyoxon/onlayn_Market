package product;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductService {
    private final ProductRepository productRepository = ProductRepository.getInstance();
    private static final ProductService productService = new ProductService();

    public void add(Product entity) {
        productRepository.add(entity);
    }

    public void delete(UUID id) {
        productRepository.delete(id);
    }

    public List<Product> getAll() {

        return productRepository.getAll();
    }

    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    public static ProductService getInstance() {
        return productService;
    }

    public List<Product> findByCategoryId(UUID categoryId) {
        List<Product> products = new ArrayList<>();
        for (Product product : getAll()) {
            if (product.getCategories_id().equals(categoryId)) {
                products.add(product);
            }
        }
        return products;
    }

}
