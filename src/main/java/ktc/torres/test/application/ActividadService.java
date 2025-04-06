package ktc.torres.test.application;

import ktc.torres.test.domain.model.Actividad;
import ktc.torres.test.infrastructure.persistence.entity.ActividadEntity;
import ktc.torres.test.infrastructure.persistence.repository.ActividadRepository;
import io.reactivex.rxjava3.core.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Propósito: Contiene la lógica de negocio o servicios de aplicación.
 * Esta clase es responsable de coordinar las operaciones entre los adaptadores de entrada y salida.
 * Osea transforma entidades a modelos si es necesario
 */
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
        ActividadEntity entity = toEntity(actividad);

        return Single.fromPublisher(
                repository.save(entity)
                        .map(this::toModel)
        );
    }

    public Completable eliminar(String id) {
        return Completable.fromPublisher(
                repository.deleteById(id)
                        .doOnSuccess(aVoid -> Mono.just("Exito en borrar la actividad: " + id))
                        .onErrorResume(throwable -> {
                            return Mono.error(new RuntimeException("Error al eliminar la actividad", throwable));
                        })
        );
    }

    private ActividadEntity toEntity(Actividad a) {
        ActividadEntity.ActividadEntityBuilder builder = ActividadEntity.builder()
                .nombre(a.getNombre())
                .estado(a.getEstado())
                .fechaHora(a.getFechaHora());

        if (a.getId() != null) {
            builder.id(a.getId());
        }

        return builder.build();
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
