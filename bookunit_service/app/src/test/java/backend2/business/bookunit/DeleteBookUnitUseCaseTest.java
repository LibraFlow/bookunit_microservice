package backend2.business.bookunit;

import backend2.persistence.BookUnitRepository;
import backend2.persistence.entity.BookUnitEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteBookUnitUseCaseTest {

    @Mock
    private BookUnitRepository bookUnitRepository;

    @InjectMocks
    private DeleteBookUnitUseCase deleteBookUnitUseCase;

    private Integer testBookUnitId;

    @BeforeEach
    void setUp() {
        testBookUnitId = 1;
    }

    @Test
    void deleteBook_Success() {
        // Arrange
        BookUnitEntity entity = BookUnitEntity.builder()
            .id(testBookUnitId)
            .bookId(100)
            .language("English")
            .pageCount(250)
            .availability(true)
            .coverImageLink("http://example.com/cover.jpg")
            .publisher("Test Publisher")
            .isbn("978-3-16-148410-0")
            .createdAt(java.time.LocalDate.now())
            .deleted(false)
            .build();
        when(bookUnitRepository.findById(testBookUnitId)).thenReturn(java.util.Optional.of(entity));
        when(bookUnitRepository.save(entity)).thenReturn(entity);

        // Act
        Void result = deleteBookUnitUseCase.deleteBook(testBookUnitId);

        // Assert
        assertNull(result);
        verify(bookUnitRepository, times(1)).findById(testBookUnitId);
        verify(bookUnitRepository, times(1)).save(entity);
    }

    @Test
    void deleteBook_BookUnitNotFound_ShouldThrowException() {
        // Arrange
        when(bookUnitRepository.findById(testBookUnitId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteBookUnitUseCase.deleteBook(testBookUnitId);
        });

        assertEquals("Book not found with id: " + testBookUnitId, exception.getMessage());
        verify(bookUnitRepository, times(1)).findById(testBookUnitId);
        verify(bookUnitRepository, never()).save(any());
    }
} 