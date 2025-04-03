package ktc.torres.test.infrastructure.persistence.repository;

import ktc.torres.test.infrastructure.persistence.entity.ActividadEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends ReactiveCrudRepository<ActividadEntity, String> {
}