package backend2.persistence;

import backend2.persistence.entity.BookUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookUnitRepository extends JpaRepository<BookUnitEntity, Integer> {
    List<BookUnitEntity> findAllByBookId(Integer bookId);
    List<BookUnitEntity> findAllByAvailabilityFalse();
}