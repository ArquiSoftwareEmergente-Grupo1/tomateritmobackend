package com.tomateritmo.arqemergente.anomalias.domain.model.commands;

public record CreateAnomaliaCommand(
        Long cultivoId,
        String nombreAnomalia,
        String descripcion,
        String imageUrl,
        Double confianza
) {}
