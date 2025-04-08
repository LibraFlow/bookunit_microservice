package backend2.business.bookunit;

import backend2.domain.BookUnitDTO;
import backend2.persistence.BookUnitRepository;
import backend2.persistence.entity.BookUnitEntity;
import backend2.business.mapper.BookUnitMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBookUnitUseCaseTest {

    @Mock
    private BookUnitRepository bookUnitRepository;

    @Mock
    private BookUnitMapper bookUnitMapper;

    @InjectMocks
    private GetBookUnitUseCase getBookUnitUseCase;

    private BookUnitDTO testBookUnitDTO;
    private BookUnitEntity testBookUnitEntity;
    private Integer testBookUnitId;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testBookUnitId = 1;
        
        testBookUnitDTO = BookUnitDTO.builder()
                .id(testBookUnitId)
                .bookId(100)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .build();

        testBookUnitEntity = BookUnitEntity.builder()
                .id(testBookUnitId)
                .bookId(100)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .createdAt(LocalDate.now())
                .build();
    }

    @Test
    void getBook_Success() {
        // Arrange
        when(bookUnitRepository.findById(testBookUnitId)).thenReturn(Optional.of(testBookUnitEntity));
        when(bookUnitMapper.toDTO(testBookUnitEntity)).thenReturn(testBookUnitDTO);

        // Act
        BookUnitDTO result = getBookUnitUseCase.getBook(testBookUnitId);

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

        // Verify interactions
        verify(bookUnitRepository, times(1)).findById(testBookUnitId);
        verify(bookUnitMapper, times(1)).toDTO(testBookUnitEntity);
    }

    @Test
    void getBook_NotFound_ShouldThrowException() {
        // Arrange
        when(bookUnitRepository.findById(testBookUnitId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            getBookUnitUseCase.getBook(testBookUnitId);
        });

        assertEquals("Book not found with id: " + testBookUnitId, exception.getMessage());

        // Verify interactions
        verify(bookUnitRepository, times(1)).findById(testBookUnitId);
        verifyNoInteractions(bookUnitMapper);
    }
} 