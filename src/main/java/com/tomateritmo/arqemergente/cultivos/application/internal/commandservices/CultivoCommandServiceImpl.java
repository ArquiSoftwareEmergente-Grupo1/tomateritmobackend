package com.tomateritmo.arqemergente.cultivos.application.internal.commandservices;

import com.tomateritmo.arqemergente.cultivos.domain.model.aggregates.Cultivo;
import com.tomateritmo.arqemergente.cultivos.domain.model.commands.*;
import com.tomateritmo.arqemergente.cultivos.domain.services.CultivoCommandService;
import com.tomateritmo.arqemergente.cultivos.infrastructure.persistence.jpa.repositories.CultivoRepository;
import com.tomateritmo.arqemergente.historial.application.internal.outboundservices.HistorialEventLogger;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CultivoCommandServiceImpl implements CultivoCommandService {
    private final CultivoRepository cultivoRepository;
    private final HistorialEventLogger historialEventLogger;

    public CultivoCommandServiceImpl(CultivoRepository cultivoRepository, HistorialEventLogger historialEventLogger) {
        this.cultivoRepository = cultivoRepository;
        this.historialEventLogger = historialEventLogger;
    }

    @Override
    public Optional<Cultivo> handle(CreateCultivoCommand command) {
        var cultivo = new Cultivo(command);
        cultivoRepository.save(cultivo);
        
        // Log event in audit trail
        historialEventLogger.logCultivoCreacion(cultivo.getNombre());
        
        return Optional.of(cultivo);
    }

    @Override
    public Optional<Cultivo> handle(Long cultivoId, UpdateCultivoCommand command) {
        return cultivoRepository.findById(cultivoId).map(cultivo -> {
            cultivo.update(command);
            cultivoRepository.save(cultivo);
            return cultivo;
        });
    }

    @Override
    public void handle(DeleteCultivoCommand command) {
        if (!cultivoRepository.existsById(command.cultivoId())) {
            throw new IllegalArgumentException("Cultivo con ID " + command.cultivoId() + " no encontrado.");
        }
        
        // Get cultivo name before deletion for audit trail
        var cultivo = cultivoRepository.findById(command.cultivoId());
        if (cultivo.isPresent()) {
            String nombreCultivo = cultivo.get().getNombre();
            
            // Delete the cultivo
            cultivoRepository.deleteById(command.cultivoId());
            
            // Log event in audit trail
            historialEventLogger.logCultivoEliminacion(nombreCultivo);
        } else {
            cultivoRepository.deleteById(command.cultivoId());
        }
    }
}
