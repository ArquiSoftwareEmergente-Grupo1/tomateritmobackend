package com.tomateritmo.arqemergente.sensores.interfaces.REST.resources;

import java.time.LocalDateTime;

public record RegistrarLecturaResource(
        String tipoSensor,
        Double valor,
        LocalDateTime timestamp,
        String sector
) {}
