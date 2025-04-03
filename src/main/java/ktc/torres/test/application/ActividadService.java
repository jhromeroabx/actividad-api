package ktc.torres.test.application;

import ktc.torres.test.domain.model.Actividad;
import ktc.torres.test.infrastructure.persistence.entity.ActividadEntity;
import ktc.torres.test.infrastructure.persistence.repository.ActividadRepository;
import io.reactivex.rxjava3.core.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActividadService {

    private final ActividadRepository repository;

    public Flowable<Actividad> listar() {
        return Flowable.fromPublisher(
                repository.findAll()
                        .map(this::toModel)
        );
    }

    public Maybe<Actividad> obtenerPorId(String id) {
        return Maybe.fromPublisher(
                repository.findById(id)
                        .map(this::toModel)
        );
    }

    public Single<Actividad> guardar(Actividad actividad) {
        if (actividad.getId() == null) {
            actividad.setId(UUID.randomUUID().toString());
        }

        ActividadEntity entity = toEntity(actividad);

        return Single.fromPublisher(
                repository.save(entity)
                        .map(this::toModel)
        );
    }

    public Completable eliminar(String id) {
        return Completable.fromPublisher(
                repository.deleteById(id)
        );
    }

    private ActividadEntity toEntity(Actividad a) {
        return ActividadEntity.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .estado(a.getEstado())
                .fechaHora(a.getFechaHora())
                .build();
    }

    private Actividad toModel(ActividadEntity e) {
        return Actividad.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .estado(e.getEstado())
                .fechaHora(e.getFechaHora())
                .build();
    }
}
