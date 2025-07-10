package com.tomateritmo.arqemergente.historial.interfaces.REST.resources;

import java.time.LocalDateTime;

public record FiltroHistorialResource(
        String tipo,
        String zona,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {
}
