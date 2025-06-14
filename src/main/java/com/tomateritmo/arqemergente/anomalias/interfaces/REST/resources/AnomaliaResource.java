package com.tomateritmo.arqemergente.anomalias.interfaces.REST.resources;

import java.time.LocalDateTime;

public record AnomaliaResource(
        Long id,
        Long cultivoId,
        String nombreAnomalia,
        String descripcion,
        String imageUrl,
        Double confianza,
        LocalDateTime fechaDeteccion
) {}
