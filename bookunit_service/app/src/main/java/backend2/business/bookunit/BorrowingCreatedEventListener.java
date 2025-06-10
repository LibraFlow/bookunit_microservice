package backend2.business.bookunit;

import backend2.domain.BorrowingCreatedEvent;
import backend2.domain.BookUnitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BorrowingCreatedEventListener {

    private final UpdateBookUnitUseCase updateBookUnitUseCase;
    private final GetBookUnitUseCase getBookUnitUseCase;

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
} 