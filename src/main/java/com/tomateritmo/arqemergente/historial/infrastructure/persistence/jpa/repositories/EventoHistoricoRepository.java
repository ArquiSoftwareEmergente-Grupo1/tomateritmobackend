package com.tomateritmo.arqemergente.historial.infrastructure.persistence.jpa.repositories;

import com.tomateritmo.arqemergente.historial.domain.model.aggregates.EventoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoHistoricoRepository extends JpaRepository<EventoHistorico, Long> {
    
    @Query("SELECT e FROM EventoHistorico e WHERE " +
           "(:tipo IS NULL OR e.evento = :tipo) AND " +
           "(:zona IS NULL OR e.cultivoName LIKE %:zona%) AND " +
           "(:fechaInicio IS NULL OR e.fecha >= :fechaInicio) AND " +
           "(:fechaFin IS NULL OR e.fecha <= :fechaFin) " +
           "ORDER BY e.fecha DESC")
    List<EventoHistorico> findByFilters(
            @Param("tipo") String tipo,
            @Param("zona") String zona,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
    
    /**
     * Encuentra todos los eventos históricos ordenados por fecha descendente (más recientes primero)
     */
    List<EventoHistorico> findAllByOrderByFechaDesc();
}
