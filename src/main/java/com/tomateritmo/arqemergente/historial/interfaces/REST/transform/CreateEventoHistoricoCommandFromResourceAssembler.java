package com.tomateritmo.arqemergente.historial.interfaces.REST.transform;

import com.tomateritmo.arqemergente.historial.domain.model.commands.CreateEventoHistoricoCommand;
import com.tomateritmo.arqemergente.historial.interfaces.REST.resources.CreateEventoHistoricoResource;

import java.time.LocalDateTime;

public class CreateEventoHistoricoCommandFromResourceAssembler {
    
    public static CreateEventoHistoricoCommand toCommandFromResource(CreateEventoHistoricoResource resource) {
        return new CreateEventoHistoricoCommand(
                LocalDateTime.now(),  // Fecha actual al momento de creación
                resource.evento(),
                resource.cultivoName(),
                resource.detalles()
        );
    }
}
