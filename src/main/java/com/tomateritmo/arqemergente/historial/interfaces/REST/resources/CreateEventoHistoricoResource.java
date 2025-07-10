package com.tomateritmo.arqemergente.historial.interfaces.REST.resources;

import java.time.LocalDateTime;

public record CreateEventoHistoricoResource(
        String evento,
        String cultivoName,
        String detalles
) {
}
