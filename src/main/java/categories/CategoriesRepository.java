package categories;

import common.BaseRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoriesRepository extends BaseRepository<UUID, Categories> {
    private static final CategoriesRepository categoriesRepository = new CategoriesRepository();

    @Override
    protected String getFileName() {
        return "src/main/resources/categories.txt";
    }

    public static CategoriesRepository getInstance() {
        return categoriesRepository;
    }

}

