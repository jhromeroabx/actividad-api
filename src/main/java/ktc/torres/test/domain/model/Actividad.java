package ktc.torres.test.domain.model;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Actividad {
    private Long id;
    private String nombre;
    private String estado;
    private LocalDateTime fechaHora;
}