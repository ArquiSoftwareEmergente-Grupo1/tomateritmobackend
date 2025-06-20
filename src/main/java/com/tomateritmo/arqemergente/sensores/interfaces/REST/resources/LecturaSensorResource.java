package com.tomateritmo.arqemergente.sensores.interfaces.REST.resources;

import java.time.LocalDateTime;

public record LecturaSensorResource(
        Long id,
        String tipoSensor,
        Double valor,
        LocalDateTime timestamp,
        String sector
) {}
