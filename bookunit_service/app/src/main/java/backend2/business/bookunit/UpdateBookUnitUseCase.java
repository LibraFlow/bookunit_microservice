package backend2.business.bookunit;

import backend2.domain.BookUnitDTO;
import backend2.persistence.entity.BookUnitEntity;
import backend2.persistence.BookUnitRepository;
import backend2.business.mapper.BookUnitMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.owasp.encoder.Encode;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateBookUnitUseCase {
    private final BookUnitRepository bookUnitRepository;
    private final BookUnitMapper bookUnitMapper;

    @Transactional
    public BookUnitDTO updateBook(Integer id, BookUnitDTO bookUnitDTO) {
        BookUnitEntity existingBook = bookUnitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        // Update existing entity with DTO values
        BookUnitEntity updatedEntity = bookUnitMapper.toEntity(bookUnitDTO);
        updatedEntity.setId(id); // Ensure we keep the same ID
        updatedEntity.setCreatedAt(existingBook.getCreatedAt()); // Preserve creation date

        BookUnitEntity savedBookUnit = bookUnitRepository.save(updatedEntity);
        return bookUnitMapper.toDTO(savedBookUnit);
    }
}