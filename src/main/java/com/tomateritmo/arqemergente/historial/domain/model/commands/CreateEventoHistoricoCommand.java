package com.tomateritmo.arqemergente.historial.domain.model.commands;

import java.time.LocalDateTime;

public record CreateEventoHistoricoCommand(
        LocalDateTime fecha,
        String evento,
        String cultivoName,
        String detalles
) {
}
