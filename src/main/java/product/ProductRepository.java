package product;

import common.BaseRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRepository extends BaseRepository<UUID, Product> {
    private static final ProductRepository productRepository = new ProductRepository();

    @Override
    protected String getFileName() {

        return "src/main/resources/products.txt";
    }

    public static ProductRepository getInstance()
    {
        return productRepository;
    }

}
