package hospital_app.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 40)
    private String nom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;

    private boolean malade;

    @Min(0) @Max(100)
    private int score;

    @OneToMany(mappedBy = "patient", targetEntity = RendezVous.class, fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;
}
