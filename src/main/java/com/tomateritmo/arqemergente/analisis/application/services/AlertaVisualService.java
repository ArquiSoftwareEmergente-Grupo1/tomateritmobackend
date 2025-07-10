package com.tomateritmo.arqemergente.analisis.application.services;

import com.tomateritmo.arqemergente.analisis.domain.model.entities.AlertaVisual;
import com.tomateritmo.arqemergente.analisis.infrastructure.persistence.jpa.repositories.AlertaVisualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertaVisualService {
    
    @Autowired
    private AlertaVisualRepository alertaRepository;
    
    // Obtener todas las alertas
    public List<AlertaVisual> obtenerTodasLasAlertas() {
        return alertaRepository.findAllByOrderByFechaDeteccionDesc();
    }
    
    // Obtener alerta por ID
    public Optional<AlertaVisual> obtenerAlertaPorId(Long id) {
        return alertaRepository.findById(id);
    }
    
    // Obtener alertas por estado
    public List<AlertaVisual> obtenerAlertasPorEstado(String estado) {
        return alertaRepository.findByEstadoOrderByFechaDeteccionDesc(estado);
    }
    
    // Obtener alertas por severidad
    public List<AlertaVisual> obtenerAlertasPorSeveridad(String severidad) {
        return alertaRepository.findBySeveridad(severidad);
    }
    
    // Obtener alertas filtradas
    public List<AlertaVisual> obtenerAlertasFiltradas(String estado, String severidad) {
        if (estado != null && !estado.isEmpty() && severidad != null && !severidad.isEmpty()) {
            return alertaRepository.findByEstadoAndSeveridad(estado, severidad);
        } else if (estado != null && !estado.isEmpty()) {
            return obtenerAlertasPorEstado(estado);
        } else if (severidad != null && !severidad.isEmpty()) {
            return obtenerAlertasPorSeveridad(severidad);
        } else {
            return obtenerTodasLasAlertas();
        }
    }
    
    // Obtener alertas por cultivo
    public List<AlertaVisual> obtenerAlertasPorCultivo(String cultivoId) {
        return alertaRepository.findByCultivoId(cultivoId);
    }
    
    // Crear nueva alerta
    public AlertaVisual crearAlerta(AlertaVisual alerta) {
        if (alerta.getFechaDeteccion() == null) {
            alerta.setFechaDeteccion(LocalDateTime.now());
        }
        if (alerta.getEstado() == null || alerta.getEstado().isEmpty()) {
            alerta.setEstado("activa");
        }
        return alertaRepository.save(alerta);
    }
    
    // Actualizar alerta
    public AlertaVisual actualizarAlerta(Long id, AlertaVisual alertaActualizada) {
        return alertaRepository.findById(id).map(alerta -> {
            if (alertaActualizada.getTitulo() != null) {
                alerta.setTitulo(alertaActualizada.getTitulo());
            }
            if (alertaActualizada.getDescripcion() != null) {
                alerta.setDescripcion(alertaActualizada.getDescripcion());
            }
            if (alertaActualizada.getSeveridad() != null) {
                alerta.setSeveridad(alertaActualizada.getSeveridad());
            }
            if (alertaActualizada.getEstado() != null) {
                alerta.setEstado(alertaActualizada.getEstado());
                // Si se resuelve la alerta, marcar fecha de resolución
                if ("resuelta".equals(alertaActualizada.getEstado())) {
                    alerta.setFechaResolucion(LocalDateTime.now());
                }
            }
            if (alertaActualizada.getAnomaliaDetectada() != null) {
                alerta.setAnomaliaDetectada(alertaActualizada.getAnomaliaDetectada());
            }
            return alertaRepository.save(alerta);
        }).orElseThrow(() -> new RuntimeException("Alerta no encontrada con id: " + id));
    }
    
    // Resolver alerta
    public AlertaVisual resolverAlerta(Long id) {
        return alertaRepository.findById(id).map(alerta -> {
            alerta.setEstado("resuelta");
            alerta.setFechaResolucion(LocalDateTime.now());
            return alertaRepository.save(alerta);
        }).orElseThrow(() -> new RuntimeException("Alerta no encontrada con id: " + id));
    }
    
    // Eliminar alerta
    public boolean eliminarAlerta(Long id) {
        if (alertaRepository.existsById(id)) {
            alertaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Obtener alertas recientes (últimos 7 días)
    public List<AlertaVisual> obtenerAlertasRecientes() {
        LocalDateTime hace7Dias = LocalDateTime.now().minusDays(7);
        return alertaRepository.findAlertasRecientes(hace7Dias);
    }
    
    // Contar alertas por estado
    public long contarAlertasPorEstado(String estado) {
        return alertaRepository.countByEstado(estado);
    }
}
