package backend2.presentation;

import backend2.domain.BookUnitDTO;
import backend2.business.bookunit.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookunits")
@RequiredArgsConstructor
public class BookUnitController {
    
    private final AddBookUnitUseCase addBookUnitUseCase;
    private final DeleteBookUnitUseCase deleteBookUnitUseCase;
    private final GetBookUnitsByBookIdUseCase getBookUnitsByBookIdUseCase;
    private final GetBookUnitUseCase getBookUnitUseCase;
    private final UpdateBookUnitUseCase updateBookUnitUseCase;
    private final OverdueUnavailableBookUnitsUseCase overdueUnavailableBookUnitsUseCase;

    @Autowired
    private HttpServletRequest request;

    @PostMapping
    public ResponseEntity<BookUnitDTO> createBookUnit(@Valid @RequestBody BookUnitDTO bookUnitDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOrLibrarian = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(role -> role.equals("ROLE_ADMINISTRATOR") || role.equals("ROLE_LIBRARIAN"));
        if (!isAdminOrLibrarian) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(addBookUnitUseCase.addBook(bookUnitDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookUnit(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOrLibrarian = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(role -> role.equals("ROLE_ADMINISTRATOR") || role.equals("ROLE_LIBRARIAN"));
        if (!isAdminOrLibrarian) {
            return ResponseEntity.status(403).build();
        }
        deleteBookUnitUseCase.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookUnitDTO>> getBookUnitsByBookId(@PathVariable Integer bookId) {
        return ResponseEntity.ok(getBookUnitsByBookIdUseCase.getBookUnitsByBookId(bookId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookUnitDTO> getBookUnit(@PathVariable Integer id) {
        BookUnitDTO bookUnit = getBookUnitUseCase.getBook(id);
        if (bookUnit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookUnit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookUnitDTO> updateBookUnit(@PathVariable Integer id, @Valid @RequestBody BookUnitDTO bookUnitDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOrLibrarian = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(role -> role.equals("ROLE_ADMINISTRATOR") || role.equals("ROLE_LIBRARIAN"));
        if (!isAdminOrLibrarian) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(updateBookUnitUseCase.updateBook(id, bookUnitDTO));
    }

    @GetMapping("/unavailable-overdue")
    public ResponseEntity<List<BookUnitDTO>> getUnavailableOverdueBookUnits() {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String jwtToken = authHeader.substring(7); // Remove "Bearer " prefix
        return ResponseEntity.ok(overdueUnavailableBookUnitsUseCase.getOverdueUnavailableBookUnits(jwtToken));
    }

    @GetMapping("/regardless/{id}")
    public ResponseEntity<BookUnitDTO> getBookUnitRegardlessOfDeleted(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOrLibrarian = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(role -> role.equals("ROLE_ADMINISTRATOR") || role.equals("ROLE_LIBRARIAN"));
        if (!isAdminOrLibrarian) {
            return ResponseEntity.status(403).build();
        }
        BookUnitDTO bookUnit = getBookUnitUseCase.getBookRegardlessOfDeleted(id);
        if (bookUnit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookUnit);
    }

    @GetMapping("/regardless/book/{bookId}")
    public ResponseEntity<List<BookUnitDTO>> getBookUnitsByBookIdRegardlessOfDeleted(@PathVariable Integer bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOrLibrarian = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(role -> role.equals("ROLE_ADMINISTRATOR") || role.equals("ROLE_LIBRARIAN"));
        if (!isAdminOrLibrarian) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(getBookUnitsByBookIdUseCase.getBookUnitsByBookIdRegardlessOfDeleted(bookId));
    }
}