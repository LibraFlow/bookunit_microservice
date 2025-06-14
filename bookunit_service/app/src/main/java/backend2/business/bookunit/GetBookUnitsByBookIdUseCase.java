package backend2.business.bookunit;

import backend2.domain.BookUnitDTO;
import backend2.persistence.BookUnitRepository;
import backend2.business.mapper.BookUnitMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetBookUnitsByBookIdUseCase {
    private final BookUnitRepository bookUnitRepository;
    private final BookUnitMapper bookUnitMapper;

    public List<BookUnitDTO> getBookUnitsByBookId(Integer bookId) {
        return bookUnitRepository.findAllByBookIdAndDeletedFalse(bookId)
                .stream()
                .map(bookUnitMapper::toDTO)
                .collect(Collectors.toList());
    }
}