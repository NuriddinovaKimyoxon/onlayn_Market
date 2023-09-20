package common;

import java.util.List;
import java.util.Optional;

public interface Repository<ID, ENTITY extends BaseEntity<ID>>{
    ENTITY add(ENTITY entity);
    void delete(ID id);
    List<ENTITY> getAll();
    Optional<ENTITY> findById(ID id);
    void update (ENTITY entity);
}
