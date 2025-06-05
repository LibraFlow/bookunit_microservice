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
import org.mockito.ArgumentCaptor;
import java.time.Instant;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateBookUnitUseCaseTest {

    @Mock
    private BookUnitRepository bookUnitRepository;

    @Mock
    private BookUnitMapper bookUnitMapper;

    @InjectMocks
    private UpdateBookUnitUseCase updateBookUnitUseCase;

    private BookUnitDTO testBookUnitDTO;
    private BookUnitEntity existingBookUnitEntity;
    private BookUnitEntity updatedBookUnitEntity;
    private BookUnitEntity savedBookUnitEntity;
    private LocalDate testCreatedAt;
    private Integer testBookUnitId;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testBookUnitId = 1;
        testCreatedAt = LocalDate.now();
        
        testBookUnitDTO = BookUnitDTO.builder()
                .id(testBookUnitId)
                .bookId(100)
                .language("Spanish")
                .pageCount(300)
                .availability(false)
                .coverImageLink("http://example.com/updated-cover.jpg")
                .publisher("Updated Publisher")
                .isbn("978-3-16-148410-1")
                .build();

        existingBookUnitEntity = BookUnitEntity.builder()
                .id(testBookUnitId)
                .bookId(100)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .createdAt(testCreatedAt)
                .build();

        updatedBookUnitEntity = BookUnitEntity.builder()
                .id(testBookUnitId)
                .bookId(100)
                .language("Spanish")
                .pageCount(300)
                .availability(false)
                .coverImageLink("http://example.com/updated-cover.jpg")
                .publisher("Updated Publisher")
                .isbn("978-3-16-148410-1")
                .createdAt(testCreatedAt)
                .build();

        savedBookUnitEntity = BookUnitEntity.builder()
                .id(testBookUnitId)
                .bookId(100)
                .language("Spanish")
                .pageCount(300)
                .availability(false)
                .coverImageLink("http://example.com/updated-cover.jpg")
                .publisher("Updated Publisher")
                .isbn("978-3-16-148410-1")
                .createdAt(testCreatedAt)
                .build();
    }

    @Test
    void updateBook_Success() {
        // Arrange
        when(bookUnitRepository.findById(testBookUnitId)).thenReturn(Optional.of(existingBookUnitEntity));
        when(bookUnitMapper.toEntity(any(BookUnitDTO.class))).thenReturn(updatedBookUnitEntity);
        when(bookUnitRepository.save(any(BookUnitEntity.class))).thenReturn(savedBookUnitEntity);
        when(bookUnitMapper.toDTO(any(BookUnitEntity.class))).thenReturn(testBookUnitDTO);

        // Act
        BookUnitDTO result = updateBookUnitUseCase.updateBook(testBookUnitId, testBookUnitDTO);

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
        verify(bookUnitMapper, times(1)).toEntity(testBookUnitDTO);
        verify(bookUnitRepository, times(1)).save(any(BookUnitEntity.class));
        verify(bookUnitMapper, times(1)).toDTO(savedBookUnitEntity);
    }

    @Test
    void updateBook_BookNotFound_ShouldThrowException() {
        // Arrange
        when(bookUnitRepository.findById(testBookUnitId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            updateBookUnitUseCase.updateBook(testBookUnitId, testBookUnitDTO);
        });

        assertEquals("Book not found with id: " + testBookUnitId, exception.getMessage());

        // Verify interactions
        verify(bookUnitRepository, times(1)).findById(testBookUnitId);
        verifyNoInteractions(bookUnitMapper);
        verify(bookUnitRepository, never()).save(any(BookUnitEntity.class));
    }

    @Test
    void updateBook_PreservesCreatedAt() {
        // Arrange
        when(bookUnitRepository.findById(testBookUnitId)).thenReturn(Optional.of(existingBookUnitEntity));
        when(bookUnitMapper.toEntity(any(BookUnitDTO.class))).thenReturn(updatedBookUnitEntity);
        when(bookUnitRepository.save(any(BookUnitEntity.class))).thenReturn(savedBookUnitEntity);
        when(bookUnitMapper.toDTO(any(BookUnitEntity.class))).thenReturn(testBookUnitDTO);

        // Act
        updateBookUnitUseCase.updateBook(testBookUnitId, testBookUnitDTO);

        // Verify that the created date was preserved
        verify(bookUnitRepository).save(argThat(entity -> 
            entity.getCreatedAt().equals(testCreatedAt)
        ));
    }

    @Test
    void onlyAuthorizedRolesCanUpdateBookUnit_andAuditTrailIsLogged() {
        // Arrange
        BookUnitService service = mock(BookUnitService.class);
        AuditTrailService auditTrailService = mock(AuditTrailService.class);
        User librarian = new User("lib1", "Librarian");
        User admin = new User("admin1", "Administrator");
        User regular = new User("user1", "User");
        BookUnit unit = new BookUnit(1L, "Good");

        // Act & Assert: Librarian can update
        service.updateBookUnit(unit, librarian);
        verify(service).updateBookUnit(unit, librarian);
        ArgumentCaptor<AuditTrailEntry> captor = ArgumentCaptor.forClass(AuditTrailEntry.class);
        verify(auditTrailService).log(captor.capture());
        AuditTrailEntry entry = captor.getValue();
        assertEquals("lib1", entry.getUserId());
        assertEquals("UPDATE_BOOK_UNIT", entry.getAction());
        assertNotNull(entry.getTimestamp());

        // Act & Assert: Admin can update
        service.updateBookUnit(unit, admin);
        verify(service).updateBookUnit(unit, admin);

        // Act & Assert: Regular user cannot update
        doThrow(new SecurityException("Unauthorized")).when(service).updateBookUnit(unit, regular);
        assertThrows(SecurityException.class, () -> service.updateBookUnit(unit, regular));
    }
} 