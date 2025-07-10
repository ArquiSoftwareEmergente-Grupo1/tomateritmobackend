package com.tomateritmo.arqemergente.historial.application.internal.queryservices;

import com.tomateritmo.arqemergente.historial.domain.model.aggregates.EventoHistorico;
import com.tomateritmo.arqemergente.historial.domain.model.queries.GetAllEventosHistoricosQuery;
import com.tomateritmo.arqemergente.historial.domain.model.queries.GetEventoHistoricoByIdQuery;
import com.tomateritmo.arqemergente.historial.domain.model.queries.GetEventosHistoricosByFilterQuery;
import com.tomateritmo.arqemergente.historial.domain.services.HistorialQueryService;
import com.tomateritmo.arqemergente.historial.infrastructure.persistence.jpa.repositories.EventoHistoricoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialQueryServiceImpl implements HistorialQueryService {
    
    private final EventoHistoricoRepository eventoHistoricoRepository;
    
    public HistorialQueryServiceImpl(EventoHistoricoRepository eventoHistoricoRepository) {
        this.eventoHistoricoRepository = eventoHistoricoRepository;
    }
    
    @Override
    public List<EventoHistorico> handle(GetAllEventosHistoricosQuery query) {
        return eventoHistoricoRepository.findAll();
    }
    
    @Override
    public Optional<EventoHistorico> handle(GetEventoHistoricoByIdQuery query) {
        return eventoHistoricoRepository.findById(query.eventoId());
    }
    
    @Override
    public List<EventoHistorico> handle(GetEventosHistoricosByFilterQuery query) {
        return eventoHistoricoRepository.findByFilters(
                query.tipo(),
                query.zona(),
                query.fechaInicio(),
                query.fechaFin()
        );
    }
    
    @Override
    public List<EventoHistorico> getAllEventosHistoricos() {
        // Usando findAll pero con ordenamiento descendente por fecha
        return eventoHistoricoRepository.findAllByOrderByFechaDesc();
    }
}
