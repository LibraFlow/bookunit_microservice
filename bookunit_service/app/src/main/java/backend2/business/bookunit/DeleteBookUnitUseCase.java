package backend2.business.bookunit;

import backend2.persistence.BookUnitRepository;
import backend2.persistence.entity.BookUnitEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteBookUnitUseCase {

    private final BookUnitRepository bookUnitRepository;

    @Transactional
    public Void deleteBook(Integer id) {
        BookUnitEntity bookUnit = bookUnitRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
        
        bookUnit.setDeleted(true);
        bookUnitRepository.save(bookUnit);
        return null;
    }

    @Transactional
    public void deleteBookUnitsByBookId(Integer bookId) {
        List<BookUnitEntity> bookUnits = bookUnitRepository.findAllByBookId(bookId);
        for (BookUnitEntity bookUnit : bookUnits) {
            bookUnit.setDeleted(true);
        }
        bookUnitRepository.saveAll(bookUnits);
    }
}