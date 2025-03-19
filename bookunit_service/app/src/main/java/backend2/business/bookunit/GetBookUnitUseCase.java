package backend2.business.bookunit;

import backend2.domain.BookUnitDTO;
import backend2.persistence.BookUnitRepository;
import backend2.business.mapper.BookUnitMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetBookUnitUseCase {
    private final BookUnitRepository bookUnitRepository;
    private final BookUnitMapper bookUnitMapper;

    @Transactional
    public BookUnitDTO getBook(Integer id) {
        return bookUnitRepository.findById(id)
                .map(bookUnitMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
}