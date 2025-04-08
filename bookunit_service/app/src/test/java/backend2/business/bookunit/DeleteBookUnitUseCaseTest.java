package backend2.business.bookunit;

import backend2.persistence.BookUnitRepository;
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
        when(bookUnitRepository.existsById(testBookUnitId)).thenReturn(true);
        doNothing().when(bookUnitRepository).deleteById(testBookUnitId);

        // Act
        Void result = deleteBookUnitUseCase.deleteBook(testBookUnitId);

        // Assert
        assertNull(result);
        verify(bookUnitRepository, times(1)).existsById(testBookUnitId);
        verify(bookUnitRepository, times(1)).deleteById(testBookUnitId);
    }

    @Test
    void deleteBook_BookUnitNotFound_ShouldThrowException() {
        // Arrange
        when(bookUnitRepository.existsById(testBookUnitId)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteBookUnitUseCase.deleteBook(testBookUnitId);
        });

        assertEquals("Book not found with id: " + testBookUnitId, exception.getMessage());
        verify(bookUnitRepository, times(1)).existsById(testBookUnitId);
        verify(bookUnitRepository, never()).deleteById(any());
    }
} 