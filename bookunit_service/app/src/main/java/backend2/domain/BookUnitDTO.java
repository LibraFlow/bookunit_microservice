package backend2.domain;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@Data
@AllArgsConstructor
public class BookUnitDTO {
    private Integer id;
    private Integer bookId;
    private String language;
    private Integer pageCount;
    private Boolean availability;
    private String coverImageLink;
    private String publisher;
    private String isbn;
    private Boolean deleted;
}