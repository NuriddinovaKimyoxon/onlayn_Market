package categories;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoriesService {
    private final CategoriesRepository categoriesRepository = CategoriesRepository.getInstance();
    private static final CategoriesService categoriesService = new CategoriesService();

    public void add(Categories entity) {

        categoriesRepository.add(entity);
    }

    public void delete(UUID id) {
        categoriesRepository.delete(id);
    }

    public List<Categories> getAll() {
        return categoriesRepository.getAll();
    }

    public Optional<Categories> findById(UUID id) {
        return categoriesRepository.findById(id);
    }

    public static CategoriesService getInstance() {
        return categoriesService;
    }
}
