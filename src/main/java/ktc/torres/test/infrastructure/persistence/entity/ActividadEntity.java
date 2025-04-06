package ktc.torres.test.infrastructure.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("actividades")
public class ActividadEntity {
    @Id
    private Long id;
    private String nombre;
    private String estado;
    @Column("fecha_hora")
    private LocalDateTime fechaHora;
}
