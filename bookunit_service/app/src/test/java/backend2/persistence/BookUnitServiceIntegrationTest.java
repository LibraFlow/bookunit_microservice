package backend2.persistence;

import backend2.persistence.entity.BookUnitEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BookUnitServiceIntegrationTest {
    @Autowired
    private BookUnitRepository bookUnitRepository;

    private BookUnitEntity testUnit;

    @BeforeEach
    void setUp() {
        // Clean up the database before each test
        bookUnitRepository.deleteAll();
        
        testUnit = BookUnitEntity.builder()
                .bookId(1)
                .language("English")
                .pageCount(300)
                .availability(true)
                .coverImageLink("http://example.com/cover.jpg")
                .publisher("Test Publisher")
                .isbn("978-3-16-148410-0")
                .createdAt(LocalDate.now())
                .build();
    }

    @Test
    void addAndFetchBookUnit() {
        BookUnitEntity saved = bookUnitRepository.save(testUnit);
        assertNotNull(saved.getId());
        BookUnitEntity byId = bookUnitRepository.findById(saved.getId()).orElseThrow();
        assertEquals(testUnit.getLanguage(), byId.getLanguage());
        assertEquals(testUnit.getBookId(), byId.getBookId());
    }

    @Test
    void updateBookUnitTest() {
        // First save the entity to get an ID
        BookUnitEntity saved = bookUnitRepository.save(testUnit);
        assertNotNull(saved.getId());
        
        // Now update it
        saved.setLanguage("French");
        BookUnitEntity updated = bookUnitRepository.save(saved);
        assertEquals("French", updated.getLanguage());
    }

    @Test
    void deleteBookUnitTest() {
        // First save the entity to get an ID
        BookUnitEntity saved = bookUnitRepository.save(testUnit);
        assertNotNull(saved.getId());
        
        // Now delete it
        bookUnitRepository.deleteById(saved.getId());
        assertFalse(bookUnitRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void findAllByBookIdTest() {
        BookUnitEntity unit2 = BookUnitEntity.builder()
                .bookId(1)
                .language("German")
                .pageCount(250)
                .availability(true)
                .coverImageLink("http://example.com/cover2.jpg")
                .publisher("Another Publisher")
                .isbn("978-3-16-148410-1")
                .createdAt(LocalDate.now())
                .build();
        bookUnitRepository.save(testUnit);
        bookUnitRepository.save(unit2);
        List<BookUnitEntity> units = bookUnitRepository.findAllByBookId(1);
        assertTrue(units.size() >= 2);
    }

    @Test
    void findAllUnavailableTest() {
        BookUnitEntity unavailable = BookUnitEntity.builder()
                .bookId(2)
                .language("Spanish")
                .pageCount(200)
                .availability(false)
                .coverImageLink("http://example.com/cover3.jpg")
                .publisher("Unavailable Publisher")
                .isbn("978-3-16-148410-2")
                .createdAt(LocalDate.now())
                .build();
        bookUnitRepository.save(testUnit);
        bookUnitRepository.save(unavailable);
        List<BookUnitEntity> units = bookUnitRepository.findAllByAvailabilityFalse();
        assertTrue(units.stream().anyMatch(u -> !u.getAvailability()));
    }

    @Test
    void saveInvalidBookUnitShouldFail() {
        BookUnitEntity invalid = BookUnitEntity.builder()
                .bookId(null) // Required field missing
                .language("") // Invalid
                .pageCount(-10) // Invalid
                .availability(true)
                .coverImageLink("not a url")
                .publisher("") // Invalid
                .isbn("invalidisbn") // Invalid
                .createdAt(LocalDate.now())
                .build();
        assertThrows(Exception.class, () -> bookUnitRepository.saveAndFlush(invalid));
    }
} 