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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AddBookUnitUseCase {
    private final BookUnitRepository bookUnitRepository;
    private final BookUnitMapper bookUnitMapper;
    private final Logger logger = LoggerFactory.getLogger(AddBookUnitUseCase.class);

    @Transactional
    public BookUnitDTO addBook(BookUnitDTO bookUnitDTO) {
        if (bookUnitDTO == null) {
            throw new IllegalArgumentException("BookUnitDTO cannot be null");
        }
        BookUnitEntity bookUnitEntity = bookUnitMapper.toEntity(bookUnitDTO);
        BookUnitEntity savedBookUnit = bookUnitRepository.save(bookUnitEntity);
        // Audit log: bookUnitId and timestamp
        logger.info("AUDIT: BookUnit added - bookUnitId={}, timestamp={}", savedBookUnit.getId(), java.time.Instant.now());
        return bookUnitMapper.toDTO(savedBookUnit);
    }
}