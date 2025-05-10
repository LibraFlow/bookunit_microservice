package backend2.presentation;

import backend2.domain.BookUnitDTO;
import backend2.business.bookunit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.junit.jupiter.api.AfterEach;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookUnitControllerTest {

    @Mock
    private AddBookUnitUseCase addBookUnitUseCase;

    @Mock
    private DeleteBookUnitUseCase deleteBookUnitUseCase;

    @Mock
    private GetBookUnitsByBookIdUseCase getBookUnitsByBookIdUseCase;

    @Mock
    private GetBookUnitUseCase getBookUnitUseCase;

    @Mock
    private UpdateBookUnitUseCase updateBookUnitUseCase;

    @InjectMocks
    private BookUnitController bookUnitController;

    private BookUnitDTO testBookUnitDTO;

    @BeforeEach
    void setUp() {
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
    }

    private void mockAuthenticationWithRole(String role) {
        Authentication authentication = mock(Authentication.class);
        Collection<? extends GrantedAuthority> authorities =
            Collections.singletonList(new SimpleGrantedAuthority(role));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createBookUnitTest() {
        mockAuthenticationWithRole("ROLE_ADMINISTRATOR");
        // Arrange
        when(addBookUnitUseCase.addBook(any(BookUnitDTO.class))).thenReturn(testBookUnitDTO);

        // Act
        ResponseEntity<BookUnitDTO> response = bookUnitController.createBookUnit(testBookUnitDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBookUnitDTO, response.getBody());
        verify(addBookUnitUseCase, times(1)).addBook(any(BookUnitDTO.class));
    }

    @Test
    void deleteBookUnitTest() {
        mockAuthenticationWithRole("ROLE_ADMINISTRATOR");
        // Arrange
        Integer bookUnitId = 1;
        doNothing().when(deleteBookUnitUseCase).deleteBook(anyInt());

        // Act
        ResponseEntity<Void> response = bookUnitController.deleteBookUnit(bookUnitId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteBookUnitUseCase, times(1)).deleteBook(bookUnitId);
    }

    @Test
    void getBookUnitsByBookIdTest() {
        // Arrange
        Integer bookId = 100;
        List<BookUnitDTO> expectedBookUnits = Arrays.asList(testBookUnitDTO);
        when(getBookUnitsByBookIdUseCase.getBookUnitsByBookId(anyInt())).thenReturn(expectedBookUnits);

        // Act
        ResponseEntity<List<BookUnitDTO>> response = bookUnitController.getBookUnitsByBookId(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBookUnits, response.getBody());
        assertEquals(1, response.getBody().size());
        verify(getBookUnitsByBookIdUseCase, times(1)).getBookUnitsByBookId(bookId);
    }

    @Test
    void getBookUnitTest() {
        // Arrange
        Integer bookUnitId = 1;
        when(getBookUnitUseCase.getBook(anyInt())).thenReturn(testBookUnitDTO);

        // Act
        ResponseEntity<BookUnitDTO> response = bookUnitController.getBookUnit(bookUnitId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBookUnitDTO, response.getBody());
        verify(getBookUnitUseCase, times(1)).getBook(bookUnitId);
    }

    @Test
    void updateBookUnitTest() {
        mockAuthenticationWithRole("ROLE_ADMINISTRATOR");
        // Arrange
        Integer bookUnitId = 1;
        when(updateBookUnitUseCase.updateBook(anyInt(), any(BookUnitDTO.class))).thenReturn(testBookUnitDTO);

        // Act
        ResponseEntity<BookUnitDTO> response = bookUnitController.updateBookUnit(bookUnitId, testBookUnitDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testBookUnitDTO, response.getBody());
        verify(updateBookUnitUseCase, times(1)).updateBook(bookUnitId, testBookUnitDTO);
    }
} 