package com.tomateritmo.arqemergente.historial.application.internal.commandservices;

import com.tomateritmo.arqemergente.historial.domain.model.aggregates.EventoHistorico;
import com.tomateritmo.arqemergente.historial.domain.model.commands.CreateEventoHistoricoCommand;
import com.tomateritmo.arqemergente.historial.domain.services.HistorialCommandService;
import com.tomateritmo.arqemergente.historial.infrastructure.persistence.jpa.repositories.EventoHistoricoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistorialCommandServiceImpl implements HistorialCommandService {
    
    private final EventoHistoricoRepository eventoHistoricoRepository;
    
    public HistorialCommandServiceImpl(EventoHistoricoRepository eventoHistoricoRepository) {
        this.eventoHistoricoRepository = eventoHistoricoRepository;
    }
    
    @Override
    public Optional<EventoHistorico> handle(CreateEventoHistoricoCommand command) {
        var eventoHistorico = new EventoHistorico(command);
        eventoHistoricoRepository.save(eventoHistorico);
        return Optional.of(eventoHistorico);
    }
}
