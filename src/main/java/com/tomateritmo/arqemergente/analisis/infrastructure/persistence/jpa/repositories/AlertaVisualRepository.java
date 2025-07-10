package com.tomateritmo.arqemergente.analisis.infrastructure.persistence.jpa.repositories;

import com.tomateritmo.arqemergente.analisis.domain.model.entities.AlertaVisual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertaVisualRepository extends JpaRepository<AlertaVisual, Long> {
    
    // Buscar alertas por estado
    List<AlertaVisual> findByEstado(String estado);
    
    // Buscar alertas por severidad
    List<AlertaVisual> findBySeveridad(String severidad);
    
    // Buscar alertas por estado y severidad
    List<AlertaVisual> findByEstadoAndSeveridad(String estado, String severidad);
    
    // Buscar alertas por cultivo ID
    List<AlertaVisual> findByCultivoId(String cultivoId);
    
    // Buscar alertas activas
    List<AlertaVisual> findByEstadoOrderByFechaDeteccionDesc(String estado);
    
    // Buscar alertas por rango de fechas
    List<AlertaVisual> findByFechaDeteccionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar alertas recientes (últimos N días)
    @Query("SELECT a FROM AlertaVisual a WHERE a.fechaDeteccion >= :fechaDesde ORDER BY a.fechaDeteccion DESC")
    List<AlertaVisual> findAlertasRecientes(@Param("fechaDesde") LocalDateTime fechaDesde);
    
    // Contar alertas por estado
    long countByEstado(String estado);
    
    // Contar alertas por severidad
    long countBySeveridad(String severidad);
    
    // Buscar todas ordenadas por fecha descendente
    List<AlertaVisual> findAllByOrderByFechaDeteccionDesc();
}
