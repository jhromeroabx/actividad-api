package ktc.torres.test.adapter;

import ktc.torres.test.application.ActividadService;
import ktc.torres.test.domain.model.Actividad;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import io.reactivex.rxjava3.schedulers.Schedulers;

@RestController
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
public class ActividadController {

    private final ActividadService service;

    @GetMapping
    public Flux<Actividad> listar() {
        return Flux.from(service.listar().subscribeOn(Schedulers.io()));
    }

    @GetMapping("/{id}")
    public Mono<Actividad> obtener(@PathVariable String id) {
        return Mono.from(service.obtenerPorId(id).toFlowable().subscribeOn(Schedulers.io()));
    }

    @PostMapping
    public Mono<Actividad> crear(@RequestBody Actividad actividad) {
        return Mono.from(service.guardar(actividad).toFlowable().subscribeOn(Schedulers.io()));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable String id) {
        return Mono.from(service.eliminar(id).toFlowable().subscribeOn(Schedulers.io())).then();
    }
}