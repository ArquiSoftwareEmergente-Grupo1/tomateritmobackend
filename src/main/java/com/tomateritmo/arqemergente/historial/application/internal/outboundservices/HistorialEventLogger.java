package com.tomateritmo.arqemergente.historial.application.internal.outboundservices;

import com.tomateritmo.arqemergente.historial.domain.model.commands.CreateEventoHistoricoCommand;
import com.tomateritmo.arqemergente.historial.domain.services.HistorialCommandService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service for registering events in the audit trail
 */
@Service
public class HistorialEventLogger {

    private final HistorialCommandService historialCommandService;

    public HistorialEventLogger(HistorialCommandService historialCommandService) {
        this.historialCommandService = historialCommandService;
    }

    /**
     * Register a cultivo creation event
     */
    public void logCultivoCreacion(String nombreCultivo) {
        CreateEventoHistoricoCommand command = new CreateEventoHistoricoCommand(
                LocalDateTime.now(),
                "CREACIÓN",
                nombreCultivo,
                "Se ha creado un nuevo cultivo: " + nombreCultivo
        );
        historialCommandService.handle(command);
    }

    /**
     * Register a cultivo deletion event
     */
    public void logCultivoEliminacion(String nombreCultivo) {
        CreateEventoHistoricoCommand command = new CreateEventoHistoricoCommand(
                LocalDateTime.now(),
                "ELIMINACIÓN",
                nombreCultivo,
                "Se ha eliminado el cultivo: " + nombreCultivo
        );
        historialCommandService.handle(command);
    }

    /**
     * Register a vision artificial analysis event
     */
    public void logAnalisisVisionArtificial(String nombreCultivo, String tipoAnalisis, String resultado) {
        CreateEventoHistoricoCommand command = new CreateEventoHistoricoCommand(
                LocalDateTime.now(),
                "ANALIZAR",
                nombreCultivo,
                "Se ha realizado un análisis de visión artificial (" + tipoAnalisis + ") en el cultivo: " + nombreCultivo + 
                ". Resultado: " + resultado
        );
        historialCommandService.handle(command);
    }
    
    /**
     * Generic method to log any type of event
     */
    public void logEvento(String tipoEvento, String nombreCultivo, String detalles) {
        CreateEventoHistoricoCommand command = new CreateEventoHistoricoCommand(
                LocalDateTime.now(),
                tipoEvento,
                nombreCultivo,
                detalles
        );
        historialCommandService.handle(command);
    }
}
