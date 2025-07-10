package com.tomateritmo.arqemergente.analisis.application.services;

import com.tomateritmo.arqemergente.analisis.domain.model.entities.AnalisisResultado;
import com.tomateritmo.arqemergente.analisis.infrastructure.persistence.jpa.repositories.AnalisisResultadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnalisisResultadoService {
    
    @Autowired
    private AnalisisResultadoRepository analisisRepository;
    
    // Obtener todos los análisis
    public List<AnalisisResultado> obtenerTodosLosAnalisis() {
        return analisisRepository.findAllByOrderByFechaAnalisisDesc();
    }
    
    // Obtener análisis por ID
    public Optional<AnalisisResultado> obtenerAnalisisPorId(Long id) {
        return analisisRepository.findById(id);
    }
    
    // Obtener análisis por cultivo
    public List<AnalisisResultado> obtenerAnalisisPorCultivo(String cultivoId) {
        return analisisRepository.findByCultivoIdOrderByFechaAnalisisDesc(cultivoId);
    }
    
    // Crear nuevo análisis
    public AnalisisResultado crearAnalisis(AnalisisResultado analisis) {
        if (analisis.getFechaAnalisis() == null) {
            analisis.setFechaAnalisis(LocalDateTime.now());
        }
        return analisisRepository.save(analisis);
    }
    
    // Actualizar análisis
    public AnalisisResultado actualizarAnalisis(Long id, AnalisisResultado analisisActualizado) {
        return analisisRepository.findById(id).map(analisis -> {
            if (analisisActualizado.getDiagnostico() != null) {
                analisis.setDiagnostico(analisisActualizado.getDiagnostico());
            }
            if (analisisActualizado.getConfirmado() != null) {
                analisis.setConfirmado(analisisActualizado.getConfirmado());
            }
            if (analisisActualizado.getAnomalia() != null) {
                analisis.setAnomalia(analisisActualizado.getAnomalia());
            }
            if (analisisActualizado.getConfianza() != null) {
                analisis.setConfianza(analisisActualizado.getConfianza());
            }
            if (analisisActualizado.getRecomendaciones() != null) {
                analisis.setRecomendaciones(analisisActualizado.getRecomendaciones());
            }
            return analisisRepository.save(analisis);
        }).orElseThrow(() -> new RuntimeException("Análisis no encontrado con id: " + id));
    }
    
    // Confirmar diagnóstico
    public AnalisisResultado confirmarDiagnostico(Long id, Boolean confirmado) {
        return analisisRepository.findById(id).map(analisis -> {
            analisis.setConfirmado(confirmado);
            return analisisRepository.save(analisis);
        }).orElseThrow(() -> new RuntimeException("Análisis no encontrado con id: " + id));
    }
    
    // Eliminar análisis
    public boolean eliminarAnalisis(Long id) {
        if (analisisRepository.existsById(id)) {
            analisisRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Obtener estadísticas
    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();
        
        long totalAnalisis = analisisRepository.count();
        long confirmados = analisisRepository.countByConfirmado(true);
        long anomaliasDetectadas = analisisRepository.countAnalisisConAnomalias();
        Double confianzaPromedio = analisisRepository.getConfianzaPromedio();
        
        estadisticas.put("totalAnalisis", totalAnalisis);
        estadisticas.put("confirmados", confirmados);
        estadisticas.put("porcentajeConfirmacion", totalAnalisis > 0 ? (confirmados * 100.0 / totalAnalisis) : 0.0);
        estadisticas.put("anomaliasDetectadas", anomaliasDetectadas);
        estadisticas.put("porcentajeAnomalias", totalAnalisis > 0 ? (anomaliasDetectadas * 100.0 / totalAnalisis) : 0.0);
        estadisticas.put("confianzaPromedio", confianzaPromedio != null ? confianzaPromedio : 0.0);
        
        return estadisticas;
    }
    
    // Obtener análisis recientes (últimos 30 días)
    public List<AnalisisResultado> obtenerAnalisisRecientes() {
        LocalDateTime hace30Dias = LocalDateTime.now().minusDays(30);
        return analisisRepository.findAnalisisRecientes(hace30Dias);
    }
    
    // Buscar análisis por anomalía
    public List<AnalisisResultado> buscarPorAnomalia(String anomalia) {
        return analisisRepository.findByAnomalia(anomalia);
    }
}
