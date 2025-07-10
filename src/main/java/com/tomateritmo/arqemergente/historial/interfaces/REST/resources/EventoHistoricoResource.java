package com.tomateritmo.arqemergente.historial.interfaces.REST.resources;

import java.time.LocalDateTime;

public record EventoHistoricoResource(
        Long id,
        LocalDateTime fecha,
        String evento,
        String cultivoName,
        String detalles
) {
}
