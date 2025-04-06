package backend2.business.mapper;

import backend2.domain.BookUnitDTO;
import backend2.persistence.entity.BookUnitEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class BookUnitMapper {
    
    public BookUnitDTO toDTO(BookUnitEntity entity) {
        if (entity == null) {
            return null;
        }

        return BookUnitDTO.builder()
                .id(entity.getId())
                .bookId(entity.getBookId())
                .language(entity.getLanguage())
                .pageCount(entity.getPageCount())
                .availability(entity.getAvailability())
                .coverImageLink(entity.getCoverImageLink())
                .publisher(entity.getPublisher())
                .isbn(entity.getIsbn())
                .build();
    }

    public BookUnitEntity toEntity(BookUnitDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return BookUnitEntity.builder()
                .id(dto.getId())
                .bookId(dto.getBookId())
                .language(dto.getLanguage())
                .pageCount(dto.getPageCount())
                .availability(dto.getAvailability())
                .coverImageLink(dto.getCoverImageLink())
                .publisher(dto.getPublisher())
                .isbn(dto.getIsbn())
                .createdAt(dto.getId() == null ? LocalDate.now() : null) // Set createdAt only for new entities
                .build();
    }
}