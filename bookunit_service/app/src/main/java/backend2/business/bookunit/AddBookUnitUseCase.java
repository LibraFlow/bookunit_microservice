package backend2.business.bookunit;

import backend2.persistence.entity.BookUnitEntity;
import backend2.domain.BookUnitDTO;
import backend2.persistence.BookUnitRepository;
import backend2.business.mapper.BookUnitMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.owasp.encoder.Encode;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AddBookUnitUseCase {
    private final BookUnitRepository bookUnitRepository;
    private final BookUnitMapper bookUnitMapper;

    @Transactional
    public BookUnitDTO addBook(BookUnitDTO bookUnitDTO) {
        BookUnitEntity bookUnitEntity = bookUnitMapper.toEntity(bookUnitDTO);
        BookUnitEntity savedBookUnit = bookUnitRepository.save(bookUnitEntity);
        return bookUnitMapper.toDTO(savedBookUnit);
    }
}