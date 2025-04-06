package backend2.business.bookunit;

import backend2.persistence.BookUnitRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteBookUnitUseCase {

    private final BookUnitRepository bookUnitRepository;

    @Transactional
    public Void deleteBook(Integer id) {
        if (!bookUnitRepository.existsById(id)) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }
        bookUnitRepository.deleteById(id);
        return null;
    }
}