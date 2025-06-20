package com.tomateritmo.arqemergente.sensores.infrastructure.persistence.jpa.repositories;

import com.tomateritmo.arqemergente.sensores.domain.model.entities.LecturaSensor;
import org.springframework.data.domain.Pageable;
import com.tomateritmo.arqemergente.sensores.domain.model.valueobjects.TipoSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LecturaSensorRepository extends JpaRepository<LecturaSensor, Long> {

    Optional<LecturaSensor> findTopByTipoSensorOrderByTimestampDesc(TipoSensor tipoSensor);

    List<LecturaSensor> findByTipoSensorInAndTimestampBetween(
            List<TipoSensor> tiposSensor,
            LocalDateTime start,
            LocalDateTime end);

    List<LecturaSensor> findByOrderByTimestampDesc(Pageable pageable);
}

