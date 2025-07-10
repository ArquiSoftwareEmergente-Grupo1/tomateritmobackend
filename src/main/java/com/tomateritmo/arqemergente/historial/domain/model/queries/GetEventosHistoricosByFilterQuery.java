package com.tomateritmo.arqemergente.historial.domain.model.queries;

import java.time.LocalDateTime;

public record GetEventosHistoricosByFilterQuery(
        String tipo,
        String zona,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {
}
