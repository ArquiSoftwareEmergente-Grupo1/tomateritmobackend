package com.tomateritmo.arqemergente.anomalias.infrastructure.persistence.jpa.repositories;

import com.tomateritmo.arqemergente.anomalias.domain.model.aggregates.Anomalia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnomaliaRepository extends JpaRepository<Anomalia, Long> {
    // Spring Data JPA crea la implementación de esta consulta automáticamente
    List<Anomalia> findByCultivoId(Long cultivoId);
}