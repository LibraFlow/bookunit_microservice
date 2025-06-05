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
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddBookUnitUseCaseTest {

    @Mock
    private BookUnitRepository bookUnitRepository;

    @Mock
    private BookUnitMapper bookUnitMapper;

    @InjectMocks
    private AddBookUnitUseCase addBookUnitUseCase;

    private BookUnitDTO testBookUnitDTO;
    private BookUnitEntity testBookUnitEntity;
    private BookUnitEntity savedBookUnitEntity;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testBookUnitDTO = BookUnitDTO.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .build();

        testBookUnitEntity = BookUnitEntity.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .createdAt(LocalDate.now())
                .build();

        savedBookUnitEntity = BookUnitEntity.builder()
                .id(1)
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
    void addBook_Success() {
        // Arrange
        when(bookUnitMapper.toEntity(any(BookUnitDTO.class))).thenReturn(testBookUnitEntity);
        when(bookUnitRepository.save(any(BookUnitEntity.class))).thenReturn(savedBookUnitEntity);
        when(bookUnitMapper.toDTO(any(BookUnitEntity.class))).thenReturn(testBookUnitDTO);

        // Act
        BookUnitDTO result = addBookUnitUseCase.addBook(testBookUnitDTO);

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
        verify(bookUnitMapper, times(1)).toEntity(testBookUnitDTO);
        verify(bookUnitRepository, times(1)).save(testBookUnitEntity);
        verify(bookUnitMapper, times(1)).toDTO(savedBookUnitEntity);
    }

    @Test
    void addBook_WithNullInput_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            addBookUnitUseCase.addBook(null);
        });

        // Verify no interactions with dependencies
        verifyNoInteractions(bookUnitRepository);
        verifyNoInteractions(bookUnitMapper);
    }

    @Test
    void addBookUnit_ShouldLogAuditTrail() {
        // Arrange
        Logger logger = (Logger) LoggerFactory.getLogger(AddBookUnitUseCase.class);
        @SuppressWarnings("unchecked")
        Appender<ILoggingEvent> mockAppender = mock(Appender.class);
        logger.addAppender(mockAppender);

        when(bookUnitMapper.toEntity(any(BookUnitDTO.class))).thenReturn(testBookUnitEntity);
        when(bookUnitRepository.save(any(BookUnitEntity.class))).thenReturn(testBookUnitEntity);
        when(bookUnitMapper.toDTO(any(BookUnitEntity.class))).thenReturn(testBookUnitDTO);

        // Act
        addBookUnitUseCase.addBook(testBookUnitDTO);

        // Assert: verify that the audit log message was produced
        verify(mockAppender, times(1)).doAppend(argThat(event ->
            event.getFormattedMessage().contains("AUDIT: BookUnit added") &&
            event.getFormattedMessage().contains("bookUnitId=" + testBookUnitEntity.getId())
        ));
        logger.detachAppender(mockAppender);
    }
} 