package com.tomateritmo.arqemergente.historial.interfaces.REST.transform;

import com.tomateritmo.arqemergente.historial.domain.model.aggregates.EventoHistorico;
import com.tomateritmo.arqemergente.historial.interfaces.REST.resources.EventoHistoricoResource;

public class EventoHistoricoResourceFromEntityAssembler {
    
    public static EventoHistoricoResource toResourceFromEntity(EventoHistorico entity) {
        return new EventoHistoricoResource(
                entity.getId(),
                entity.getFecha(),
                entity.getEvento(),
                entity.getCultivoName(),
                entity.getDetalles()
        );
    }
}
