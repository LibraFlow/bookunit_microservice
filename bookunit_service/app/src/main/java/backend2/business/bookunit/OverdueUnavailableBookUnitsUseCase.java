package backend2.business.bookunit;

import backend2.domain.BookUnitDTO;
import backend2.persistence.BookUnitRepository;
import backend2.business.mapper.BookUnitMapper;
import backend2.persistence.entity.BookUnitEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OverdueUnavailableBookUnitsUseCase {
    private final BookUnitRepository bookUnitRepository;
    private final BookUnitMapper bookUnitMapper;
    private final BorrowingServiceClient borrowingServiceClient;

    public List<BookUnitDTO> getOverdueUnavailableBookUnits(String jwtToken) {
        List<BookUnitEntity> unavailableUnits = bookUnitRepository.findAllByAvailabilityFalseAndDeletedFalse();
        LocalDate now = LocalDate.now();
        return unavailableUnits.stream()
            .filter(unit -> {
                List<?> borrowings = borrowingServiceClient.getBorrowingsByBookUnitId(unit.getId(), jwtToken);
                // If all borrowings have endDate < now, it's overdue
                return borrowings.stream().allMatch(b -> {
                    Object endDateObj = ((java.util.Map<?, ?>)b).get("endDate");
                    if (endDateObj == null) return true;
                    LocalDate endDate = LocalDate.parse(endDateObj.toString());
                    return endDate.isBefore(now);
                });
            })
            .map(bookUnitMapper::toDTO)
            .collect(Collectors.toList());
    }
} 