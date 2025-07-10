package com.tomateritmo.arqemergente.historial.domain.services;

import com.tomateritmo.arqemergente.historial.domain.model.aggregates.EventoHistorico;
import com.tomateritmo.arqemergente.historial.domain.model.queries.GetAllEventosHistoricosQuery;
import com.tomateritmo.arqemergente.historial.domain.model.queries.GetEventoHistoricoByIdQuery;
import com.tomateritmo.arqemergente.historial.domain.model.queries.GetEventosHistoricosByFilterQuery;

import java.util.List;
import java.util.Optional;

public interface HistorialQueryService {
    List<EventoHistorico> handle(GetAllEventosHistoricosQuery query);
    Optional<EventoHistorico> handle(GetEventoHistoricoByIdQuery query);
    List<EventoHistorico> handle(GetEventosHistoricosByFilterQuery query);
    
    /**
     * Obtiene todos los eventos históricos ordenados por fecha descendente (más recientes primero).
     * Método utilitario para facilitar el acceso a todos los eventos sin crear una query.
     * @return Lista de todos los eventos históricos ordenados cronológicamente
     */
    List<EventoHistorico> getAllEventosHistoricos();
}
