package backend2.business.bookunit;

import backend2.domain.BorrowingCreatedEvent;
import backend2.domain.BookUnitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class BorrowingCreatedEventListener {

    private final UpdateBookUnitUseCase updateBookUnitUseCase;
    private final GetBookUnitUseCase getBookUnitUseCase;
    private final DeleteBookUnitUseCase deleteBookUnitUseCase;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "borrowing.created")
    public void handleBorrowingCreated(BorrowingCreatedEvent event) {
        System.out.println("Received BorrowingCreatedEvent for bookUnitId: " + event.getBookUnitId());
        // Get the book unit
        BookUnitDTO bookUnit = getBookUnitUseCase.getBook(event.getBookUnitId());
        
        // Update availability to false
        bookUnit.setAvailability(false);
        
        // Save the updated book unit
        updateBookUnitUseCase.updateBook(event.getBookUnitId(), bookUnit);
    }

    @RabbitListener(queues = "book.deleted")
    public void handleBookDeleted(String jsonBookId) {
        try {
            Integer bookId = objectMapper.readValue(jsonBookId, Integer.class);
            System.out.println("Received BookDeletedEvent for bookId: " + bookId);
            deleteBookUnitUseCase.deleteBookUnitsByBookId(bookId);
        } catch (Exception e) {
            System.err.println("Failed to parse bookId from book.deleted event: " + e.getMessage());
        }
    }
} 