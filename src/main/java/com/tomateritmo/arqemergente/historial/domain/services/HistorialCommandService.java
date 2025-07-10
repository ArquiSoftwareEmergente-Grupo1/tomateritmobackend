package com.tomateritmo.arqemergente.historial.domain.services;

import com.tomateritmo.arqemergente.historial.domain.model.aggregates.EventoHistorico;
import com.tomateritmo.arqemergente.historial.domain.model.commands.CreateEventoHistoricoCommand;

import java.util.Optional;

public interface HistorialCommandService {
    Optional<EventoHistorico> handle(CreateEventoHistoricoCommand command);
}
