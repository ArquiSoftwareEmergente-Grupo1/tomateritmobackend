package com.tomateritmo.arqemergente.sensores.domain.model.commands;

import java.time.LocalDateTime;

public record RegistrarLecturaSensorCommand(
        String tipoSensor,
        Double valor,
        LocalDateTime timestamp,
        String sector
) {}
