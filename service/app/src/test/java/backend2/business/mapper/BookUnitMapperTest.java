package backend2.business.mapper;

import backend2.domain.BookUnitDTO;
import backend2.persistence.entity.BookUnitEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookUnitMapperTest {

    @InjectMocks
    private BookUnitMapper bookUnitMapper;

    private BookUnitEntity testBookUnitEntity;
    private BookUnitDTO testBookUnitDTO;

    @BeforeEach
    void setUp() {
        // Initialize test data for BookUnitEntity
        testBookUnitEntity = BookUnitEntity.builder()
                .id(1)
                .bookId(1)
                .language("English")
                .pageCount(200)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .createdAt(LocalDate.now())
                .build();

        // Initialize test data for BookUnitDTO
        testBookUnitDTO = BookUnitDTO.builder()
                .id(1)
                .bookId(1)
                .language("English")
                .pageCount(200)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .build();
    }

    @Test
    void toDTO_WithValidEntity_ShouldReturnCorrectDTO() {
        // Act
        BookUnitDTO result = bookUnitMapper.toDTO(testBookUnitEntity);

        // Assert
        assertNotNull(result);
        assertEquals(testBookUnitEntity.getId(), result.getId());
        assertEquals(testBookUnitEntity.getBookId(), result.getBookId());
        assertEquals(testBookUnitEntity.getLanguage(), result.getLanguage());
        assertEquals(testBookUnitEntity.getPageCount(), result.getPageCount());
        assertEquals(testBookUnitEntity.getAvailability(), result.getAvailability());
        assertEquals(testBookUnitEntity.getCoverImageLink(), result.getCoverImageLink());
        assertEquals(testBookUnitEntity.getPublisher(), result.getPublisher());
        assertEquals(testBookUnitEntity.getIsbn(), result.getIsbn());
    }

    @Test
    void toDTO_WithNullEntity_ShouldReturnNull() {
        // Act
        BookUnitDTO result = bookUnitMapper.toDTO(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntity_WithValidDTO_ShouldReturnCorrectEntity() {
        // Act
        BookUnitEntity result = bookUnitMapper.toEntity(testBookUnitDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testBookUnitDTO.getId(), result.getId());
        assertEquals(testBookUnitDTO.getBookId(), result.getBookId());
        assertEquals(testBookUnitDTO.getLanguage(), result.getLanguage());
        assertEquals(testBookUnitDTO.getPageCount(), result.getPageCount());
        assertEquals(testBookUnitDTO.getAvailability(), result.getAvailability());
        assertEquals(testBookUnitDTO.getCoverImageLink(), result.getCoverImageLink());
        assertEquals(testBookUnitDTO.getPublisher(), result.getPublisher());
        assertEquals(testBookUnitDTO.getIsbn(), result.getIsbn());
    }

    @Test
    void toEntity_WithNullDTO_ShouldReturnNull() {
        // Act
        BookUnitEntity result = bookUnitMapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntity_WithNewBookUnit_ShouldSetCreatedAt() {
        // Arrange
        BookUnitDTO newBookUnitDTO = BookUnitDTO.builder()
                .bookId(1)
                .language("Spanish")
                .pageCount(150)
                .availability(true)
                .coverImageLink("http://example.com/new-cover.jpg")
                .publisher("New Publisher")
                .isbn("978-3-16-148410-1")
                .build();

        // Act
        BookUnitEntity result = bookUnitMapper.toEntity(newBookUnitDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getCreatedAt());
        assertEquals(LocalDate.now(), result.getCreatedAt());
    }

    @Test
    void toEntity_WithExistingBookUnit_ShouldNotSetCreatedAt() {
        // Act
        BookUnitEntity result = bookUnitMapper.toEntity(testBookUnitDTO);

        // Assert
        assertNotNull(result);
        assertNull(result.getCreatedAt());
    }
} 