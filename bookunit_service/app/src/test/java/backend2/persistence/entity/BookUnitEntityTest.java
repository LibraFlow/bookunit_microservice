package backend2.persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookUnitEntityTest {

    private BookUnitEntity bookUnitEntity;

    @BeforeEach
    void setUp() {
        bookUnitEntity = new BookUnitEntity();
    }

    @Test
    void testBookUnitEntityBuilder() {
        LocalDate testDate = LocalDate.now();
        
        BookUnitEntity builtEntity = BookUnitEntity.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(300)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .createdAt(testDate)
                .build();

        assertNotNull(builtEntity);
        assertEquals(1, builtEntity.getId());
        assertEquals(100, builtEntity.getBookId());
        assertEquals("English", builtEntity.getLanguage());
        assertEquals(300, builtEntity.getPageCount());
        assertTrue(builtEntity.getAvailability());
        assertEquals("http://example.com/cover.jpg", builtEntity.getCoverImageLink());
        assertEquals("Test Publisher", builtEntity.getPublisher());
        assertEquals("978-3-16-148410-0", builtEntity.getIsbn());
        assertEquals(testDate, builtEntity.getCreatedAt());
    }

    @Test
    void testBookUnitEntitySettersAndGetters() {
        LocalDate testDate = LocalDate.now();

        bookUnitEntity.setId(1);
        bookUnitEntity.setBookId(100);
        bookUnitEntity.setLanguage("English");
        bookUnitEntity.setPageCount(300);
        bookUnitEntity.setAvailability(true);
        bookUnitEntity.setCoverImageLink("http://example.com/cover.jpg");
        bookUnitEntity.setPublisher("Test Publisher");
        bookUnitEntity.setIsbn("978-3-16-148410-0");
        bookUnitEntity.setCreatedAt(testDate);

        assertEquals(1, bookUnitEntity.getId());
        assertEquals(100, bookUnitEntity.getBookId());
        assertEquals("English", bookUnitEntity.getLanguage());
        assertEquals(300, bookUnitEntity.getPageCount());
        assertTrue(bookUnitEntity.getAvailability());
        assertEquals("http://example.com/cover.jpg", bookUnitEntity.getCoverImageLink());
        assertEquals("Test Publisher", bookUnitEntity.getPublisher());
        assertEquals("978-3-16-148410-0", bookUnitEntity.getIsbn());
        assertEquals(testDate, bookUnitEntity.getCreatedAt());
    }

    @Test
    void testBookUnitEntityEqualsAndHashCode() {
        LocalDate testDate = LocalDate.now();
        
        BookUnitEntity entity1 = BookUnitEntity.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(300)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .createdAt(testDate)
                .build();

        BookUnitEntity entity2 = BookUnitEntity.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(300)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .createdAt(testDate)
                .build();

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void testBookUnitEntityToString() {
        LocalDate testDate = LocalDate.now();
        
        BookUnitEntity entity = BookUnitEntity.builder()
                .id(1)
                .bookId(100)
                .language("English")
                .pageCount(300)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .createdAt(testDate)
                .build();

        String toString = entity.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("bookId=100"));
        assertTrue(toString.contains("language=English"));
        assertTrue(toString.contains("pageCount=300"));
        assertTrue(toString.contains("availability=true"));
        assertTrue(toString.contains("coverImageLink=http://example.com/cover.jpg"));
        assertTrue(toString.contains("publisher=Test Publisher"));
        assertTrue(toString.contains("isbn=978-3-16-148410-0"));
        assertTrue(toString.contains("createdAt=" + testDate));
    }

    @Test
    void testBookUnitEntityNoArgsConstructor() {
        BookUnitEntity entity = new BookUnitEntity();
        assertNotNull(entity);
    }

    @Test
    void testBookUnitEntityAllArgsConstructor() {
        LocalDate testDate = LocalDate.now();
        
        BookUnitEntity entity = new BookUnitEntity(1, 100, "English", 300, true,
                "http://example.com/cover.jpg", "Test Publisher", "978-3-16-148410-0", testDate);

        assertEquals(1, entity.getId());
        assertEquals(100, entity.getBookId());
        assertEquals("English", entity.getLanguage());
        assertEquals(300, entity.getPageCount());
        assertTrue(entity.getAvailability());
        assertEquals("http://example.com/cover.jpg", entity.getCoverImageLink());
        assertEquals("Test Publisher", entity.getPublisher());
        assertEquals("978-3-16-148410-0", entity.getIsbn());
        assertEquals(testDate, entity.getCreatedAt());
    }
} 