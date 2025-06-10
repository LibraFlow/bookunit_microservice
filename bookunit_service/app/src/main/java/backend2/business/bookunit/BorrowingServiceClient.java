package backend2.business.bookunit;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;

@Service
public class BorrowingServiceClient {
    private final WebClient webClient;

    public BorrowingServiceClient(@Value("${borrowing.service.url:http://localhost:8086}") String borrowingServiceUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(borrowingServiceUrl)
            .build();
    }

    public List<Map<String, Object>> getBorrowingsByBookUnitId(Integer bookUnitId, String jwtToken) {
        return webClient.get()
            .uri("/api/v1/borrowings/by-bookunit/{bookUnitId}", bookUnitId)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
            .block();
    }
} 