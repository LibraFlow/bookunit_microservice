package backend2.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "bookunits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookUnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer bookId;

    @NotBlank
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String language;

    @NotNull
    @Positive
    @Max(10000)
    @Column(nullable = false)
    private Integer pageCount;

    @NotNull
    @Column(nullable = false)
    private Boolean availability;
    
    // @URL
    private String coverImageLink;

    @NotBlank
    @Size(min = 1, max = 40)
    @Column(nullable = false, length = 40)
    private String publisher;

    @NotBlank
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ])?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$")
    @Column(nullable = false)
    private String isbn;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate createdAt;
}