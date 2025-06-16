package backend2.business.bookunit;

import backend2.domain.BorrowingCreatedEvent;
import backend2.domain.BookUnitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;

@Component
@RequiredArgsConstructor
public class BorrowingCreatedEventListener {

    private final UpdateBookUnitUseCase updateBookUnitUseCase;
    private final GetBookUnitUseCase getBookUnitUseCase;
    private final DeleteBookUnitUseCase deleteBookUnitUseCase;
    private final ObjectMapper objectMapper;

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

    @KafkaListener(topics = "book.deleted", groupId = "libraflow-group")
    public void handleBookDeletedKafka(String jsonBookId) {
        try {
            Integer bookId = objectMapper.readValue(jsonBookId, Integer.class);
            System.out.println("Received BookDeletedEvent from Kafka for bookId: " + bookId);
            deleteBookUnitUseCase.deleteBookUnitsByBookId(bookId);
        } catch (Exception e) {
            System.err.println("Failed to parse bookId from book.deleted Kafka event: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "borrowing.created", groupId = "libraflow-group")
    public void handleBorrowingCreatedKafka(String jsonEvent) {
        try {
            BorrowingCreatedEvent event = objectMapper.readValue(jsonEvent, BorrowingCreatedEvent.class);
            System.out.println("Received BorrowingCreatedEvent from Kafka for bookUnitId: " + event.getBookUnitId());
        // Get the book unit
        BookUnitDTO bookUnit = getBookUnitUseCase.getBook(event.getBookUnitId());
        // Update availability to false
        bookUnit.setAvailability(false);
        // Save the updated book unit
        updateBookUnitUseCase.updateBook(event.getBookUnitId(), bookUnit);
        } catch (Exception e) {
            System.err.println("Failed to parse BorrowingCreatedEvent from borrowing.created Kafka event: " + e.getMessage());
        }
    }
} 