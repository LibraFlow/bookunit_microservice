package backend2.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookUnitDTOTest {

    @Test
    void testBookUnitDTOBuilder() {
        // Arrange & Act
        BookUnitDTO bookUnitDTO = BookUnitDTO.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .build();

        // Assert
        assertNotNull(bookUnitDTO);
        assertEquals(1, bookUnitDTO.getId());
        assertEquals(100, bookUnitDTO.getBookId());
        assertEquals("English", bookUnitDTO.getLanguage());
        assertEquals(250, bookUnitDTO.getPageCount());
        assertTrue(bookUnitDTO.getAvailability());
        assertEquals("http://example.com/cover.jpg", bookUnitDTO.getCoverImageLink());
        assertEquals("Test Publisher", bookUnitDTO.getPublisher());
        assertEquals("978-3-16-148410-0", bookUnitDTO.getIsbn());
    }

    @Test
    void testBookUnitDTOAllArgsConstructor() {
        // Arrange & Act
        BookUnitDTO bookUnitDTO = new BookUnitDTO(
            1, 100, "English", 250, true,
            "http://example.com/cover.jpg", "Test Publisher", "978-3-16-148410-0"
        );

        // Assert
        assertNotNull(bookUnitDTO);
        assertEquals(1, bookUnitDTO.getId());
        assertEquals(100, bookUnitDTO.getBookId());
        assertEquals("English", bookUnitDTO.getLanguage());
        assertEquals(250, bookUnitDTO.getPageCount());
        assertTrue(bookUnitDTO.getAvailability());
        assertEquals("http://example.com/cover.jpg", bookUnitDTO.getCoverImageLink());
        assertEquals("Test Publisher", bookUnitDTO.getPublisher());
        assertEquals("978-3-16-148410-0", bookUnitDTO.getIsbn());
    }

    @Test
    void testBookUnitDTOEqualsAndHashCode() {
        // Arrange
        BookUnitDTO bookUnitDTO1 = BookUnitDTO.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .build();

        BookUnitDTO bookUnitDTO2 = BookUnitDTO.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .build();

        BookUnitDTO bookUnitDTO3 = BookUnitDTO.builder()
                .id(2)
                .bookId(200)
                .language("Spanish")
                .pageCount(300)
                .availability(false)
                .coverImageLink("http://example.com/cover2.jpg")
                .publisher("Different Publisher")
                .isbn("978-3-16-148410-1")
                .build();

        // Assert
        assertEquals(bookUnitDTO1, bookUnitDTO2);
        assertNotEquals(bookUnitDTO1, bookUnitDTO3);
        assertEquals(bookUnitDTO1.hashCode(), bookUnitDTO2.hashCode());
        assertNotEquals(bookUnitDTO1.hashCode(), bookUnitDTO3.hashCode());
    }

    @Test
    void testBookUnitDTOToString() {
        // Arrange
        BookUnitDTO bookUnitDTO = BookUnitDTO.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .build();

        // Act
        String toString = bookUnitDTO.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("bookId=100"));
        assertTrue(toString.contains("language=English"));
        assertTrue(toString.contains("pageCount=250"));
        assertTrue(toString.contains("availability=true"));
        assertTrue(toString.contains("coverImageLink=http://example.com/cover.jpg"));
        assertTrue(toString.contains("publisher=Test Publisher"));
        assertTrue(toString.contains("isbn=978-3-16-148410-0"));
    }
} 