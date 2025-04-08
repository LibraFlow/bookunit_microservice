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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBookUnitsByBookIdUseCaseTest {

    @Mock
    private BookUnitRepository bookUnitRepository;

    @Mock
    private BookUnitMapper bookUnitMapper;

    @InjectMocks
    private GetBookUnitsByBookIdUseCase getBookUnitsByBookIdUseCase;

    private BookUnitEntity testBookUnitEntity1;
    private BookUnitEntity testBookUnitEntity2;
    private BookUnitDTO testBookUnitDTO1;
    private BookUnitDTO testBookUnitDTO2;
    private List<BookUnitEntity> testBookUnitEntities;
    private List<BookUnitDTO> expectedBookUnitDTOs;
    private Integer testBookId;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testBookId = 100;
        
        testBookUnitEntity1 = BookUnitEntity.builder()
                .id(1)
                .bookId(testBookId)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover1.jpg")
                .publisher("Test Publisher 1")
                .isbn("978-3-16-148410-0")
                .createdAt(LocalDate.now())
                .build();

        testBookUnitEntity2 = BookUnitEntity.builder()
                .id(2)
                .bookId(testBookId)
                .language("Spanish")
                .pageCount(300)
                .availability(false)
                .coverImageLink("http://example.com/cover2.jpg")
                .publisher("Test Publisher 2")
                .isbn("978-3-16-148410-1")
                .createdAt(LocalDate.now())
                .build();

        testBookUnitDTO1 = BookUnitDTO.builder()
                .id(1)
                .bookId(testBookId)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover1.jpg")
                .publisher("Test Publisher 1")
                .isbn("978-3-16-148410-0")
                .build();

        testBookUnitDTO2 = BookUnitDTO.builder()
                .id(2)
                .bookId(testBookId)
                .language("Spanish")
                .pageCount(300)
                .availability(false)
                .coverImageLink("http://example.com/cover2.jpg")
                .publisher("Test Publisher 2")
                .isbn("978-3-16-148410-1")
                .build();

        testBookUnitEntities = Arrays.asList(testBookUnitEntity1, testBookUnitEntity2);
        expectedBookUnitDTOs = Arrays.asList(testBookUnitDTO1, testBookUnitDTO2);
    }

    @Test
    void getBookUnitsByBookId_Success() {
        // Arrange
        when(bookUnitRepository.findAllByBookId(testBookId)).thenReturn(testBookUnitEntities);
        when(bookUnitMapper.toDTO(testBookUnitEntity1)).thenReturn(testBookUnitDTO1);
        when(bookUnitMapper.toDTO(testBookUnitEntity2)).thenReturn(testBookUnitDTO2);

        // Act
        List<BookUnitDTO> result = getBookUnitsByBookIdUseCase.getBookUnitsByBookId(testBookId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedBookUnitDTOs, result);

        // Verify interactions
        verify(bookUnitRepository, times(1)).findAllByBookId(testBookId);
        verify(bookUnitMapper, times(1)).toDTO(testBookUnitEntity1);
        verify(bookUnitMapper, times(1)).toDTO(testBookUnitEntity2);
    }

    @Test
    void getBookUnitsByBookId_EmptyResult() {
        // Arrange
        when(bookUnitRepository.findAllByBookId(testBookId)).thenReturn(Collections.emptyList());

        // Act
        List<BookUnitDTO> result = getBookUnitsByBookIdUseCase.getBookUnitsByBookId(testBookId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Verify interactions
        verify(bookUnitRepository, times(1)).findAllByBookId(testBookId);
        verify(bookUnitMapper, never()).toDTO(any());
    }

    @Test
    void getBookUnitsByBookId_WithNullBookId_ShouldReturnEmptyList() {
        // Act
        List<BookUnitDTO> result = getBookUnitsByBookIdUseCase.getBookUnitsByBookId(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Verify interactions
        verify(bookUnitRepository, times(1)).findAllByBookId(null);
        verify(bookUnitMapper, never()).toDTO(any());
    }
} 