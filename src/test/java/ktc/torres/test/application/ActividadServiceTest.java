package ktc.torres.test.application;

import static org.mockito.Mockito.*;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import ktc.torres.test.domain.model.Actividad;
import ktc.torres.test.infrastructure.persistence.entity.ActividadEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import io.reactivex.rxjava3.core.Completable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ktc.torres.test.infrastructure.persistence.repository.ActividadRepository;

import java.time.LocalDateTime;

public class ActividadServiceTest {

    @Mock
    private ActividadRepository repository;

    @InjectMocks
    private ActividadService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void eliminar_completa_sin_errores() {
        String id = "testId";
        when(repository.deleteById(id)).thenReturn(Mono.empty());

        Completable result = service.eliminar(id);

        result.test()
                .assertComplete()
                .assertNoErrors();

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void eliminar_lanza_error() {
        String id = "testId";
        RuntimeException exception = new RuntimeException("Error al eliminar la actividad");
        when(repository.deleteById(id)).thenReturn(Mono.error(exception));

        Completable result = service.eliminar(id);

        result.test()
                .assertError(throwable ->
                        throwable instanceof RuntimeException
                                && throwable.getMessage().equals("Error al eliminar la actividad"));

        verify(repository, times(1)).deleteById(id);
    }


    @Test
    void listar_devuelve_lista_de_actividades() {
        ActividadEntity entity1 = new ActividadEntity(1L, "Actividad 1", "Activa", LocalDateTime.parse("2023-10-01T10:00:00"));
        ActividadEntity entity2 = new ActividadEntity(2L, "Actividad 2", "Inactiva", LocalDateTime.parse("2023-10-01T10:00:00"));
        when(repository.findAll()).thenReturn(Flux.just(entity1, entity2));

        Flowable<Actividad> result = service.listar();

        result.test()
                .assertValueAt(0, actividad -> actividad.getId().equals(1L) && actividad.getNombre().equals("Actividad 1"))
                .assertValueAt(1, actividad -> actividad.getId().equals(2L) && actividad.getNombre().equals("Actividad 2"))
                .assertComplete()
                .assertNoErrors();

        verify(repository, times(1)).findAll();
    }

    @Test
    void listar_devuelve_lista_vacia() {
        when(repository.findAll()).thenReturn(Flux.empty());

        Flowable<Actividad> result = service.listar();

        result.test()
                .assertComplete()
                .assertNoValues()
                .assertNoErrors();

        verify(repository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_devuelve_actividad() {
        Long id = 1L;
        ActividadEntity entity = new ActividadEntity(id, "Actividad Test", "Activa", LocalDateTime.parse("2023-10-01T10:00:00"));
        when(repository.findById(String.valueOf(id))).thenReturn(Mono.just(entity));

        Maybe<Actividad> result = service.obtenerPorId(String.valueOf(id));

        result.test()
                .assertValue(actividad -> actividad.getId().equals(id) && actividad.getNombre().equals("Actividad Test"))
                .assertComplete()
                .assertNoErrors();

        verify(repository, times(1)).findById(String.valueOf(id));
    }

    @Test
    void obtenerPorId_devuelve_vacio() {
        String id = "testId";
        when(repository.findById(id)).thenReturn(Mono.empty());

        Maybe<Actividad> result = service.obtenerPorId(id);

        result.test()
                .assertComplete()
                .assertNoValues()
                .assertNoErrors();

        verify(repository, times(1)).findById(id);
    }

    @Test
    void guardar_devuelve_actividad_guardada() {
        Actividad actividad = new Actividad(1L, "Actividad Test", "Activa", LocalDateTime.parse("2023-10-01T10:00:00"));
        ActividadEntity entity = new ActividadEntity(1L, "Actividad Test", "Activa", LocalDateTime.parse("2023-10-01T10:00:00"));
        when(repository.save(any(ActividadEntity.class))).thenReturn(Mono.just(entity));

        Single<Actividad> result = service.guardar(actividad);

        result.test()
                .assertValue(savedActividad -> savedActividad.getId().equals(1L) && savedActividad.getNombre().equals("Actividad Test"))
                .assertComplete()
                .assertNoErrors();

        verify(repository, times(1)).save(any(ActividadEntity.class));
    }

    @Test
    void guardar_lanza_error() {
        Actividad actividad = new Actividad(1L, "Actividad Test", "Activa", LocalDateTime.parse("2023-10-01T10:00:00"));
        RuntimeException exception = new RuntimeException("Error al guardar la actividad");
        when(repository.save(any(ActividadEntity.class))).thenReturn(Mono.error(exception));

        Single<Actividad> result = service.guardar(actividad);

        result.test()
                .assertError(throwable ->
                        throwable instanceof RuntimeException
                                && throwable.getMessage().equals("Error al guardar la actividad"));

        verify(repository, times(1)).save(any(ActividadEntity.class));
    }
}