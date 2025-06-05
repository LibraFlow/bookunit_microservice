package backend2.business.bookunit;

import backend2.domain.BookUnitDTO;
import backend2.persistence.entity.BookUnitEntity;
import backend2.persistence.BookUnitRepository;
import backend2.business.mapper.BookUnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.owasp.encoder.Encode;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateBookUnitUseCase {
    private final BookUnitRepository bookUnitRepository;
    private final BookUnitMapper bookUnitMapper;
    private final Logger logger = LoggerFactory.getLogger(UpdateBookUnitUseCase.class);

    @Transactional
    public BookUnitDTO updateBook(Integer id, BookUnitDTO bookUnitDTO) {
        BookUnitEntity existingBook = bookUnitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Update existing entity with DTO values
        BookUnitEntity updatedEntity = bookUnitMapper.toEntity(bookUnitDTO);
        updatedEntity.setId(id); // Ensure we keep the same ID
        updatedEntity.setCreatedAt(existingBook.getCreatedAt()); // Preserve creation date

        BookUnitEntity savedBookUnit = bookUnitRepository.save(updatedEntity);
        // Audit log: bookUnitId and timestamp
        logger.info("AUDIT: BookUnit updated - bookUnitId={}, timestamp={}", savedBookUnit.getId(), java.time.Instant.now());
        return bookUnitMapper.toDTO(savedBookUnit);
    }
}