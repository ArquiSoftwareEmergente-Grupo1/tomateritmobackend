package com.tomateritmo.arqemergente.analisis.infrastructure.persistence.jpa.repositories;

import com.tomateritmo.arqemergente.analisis.domain.model.entities.AnalisisResultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalisisResultadoRepository extends JpaRepository<AnalisisResultado, Long> {
    
    // Buscar análisis por cultivo ID
    List<AnalisisResultado> findByCultivoId(String cultivoId);
    
    // Buscar análisis por cultivo ID ordenados por fecha
    List<AnalisisResultado> findByCultivoIdOrderByFechaAnalisisDesc(String cultivoId);
    
    // Buscar análisis confirmados
    List<AnalisisResultado> findByConfirmado(Boolean confirmado);
    
    // Buscar análisis por anomalía
    List<AnalisisResultado> findByAnomalia(String anomalia);
    
    // Buscar análisis por rango de fechas
    List<AnalisisResultado> findByFechaAnalisisBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Buscar análisis recientes (últimos N días)
    @Query("SELECT a FROM AnalisisResultado a WHERE a.fechaAnalisis >= :fechaDesde ORDER BY a.fechaAnalisis DESC")
    List<AnalisisResultado> findAnalisisRecientes(@Param("fechaDesde") LocalDateTime fechaDesde);
    
    // Contar análisis por cultivo
    long countByCultivoId(String cultivoId);
    
    // Contar análisis confirmados
    long countByConfirmado(Boolean confirmado);
    
    // Contar análisis con anomalías (excluyendo 'healthy')
    @Query("SELECT COUNT(a) FROM AnalisisResultado a WHERE a.anomalia != 'healthy' AND a.anomalia IS NOT NULL")
    long countAnalisisConAnomalias();
    
    // Obtener confianza promedio
    @Query("SELECT AVG(a.confianza) FROM AnalisisResultado a WHERE a.confianza IS NOT NULL")
    Double getConfianzaPromedio();
    
    // Buscar todos ordenados por fecha descendente
    List<AnalisisResultado> findAllByOrderByFechaAnalisisDesc();
}
