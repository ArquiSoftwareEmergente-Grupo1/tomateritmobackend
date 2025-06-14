package com.tomateritmo.arqemergente.sensores.domain.model.queries;

import com.tomateritmo.arqemergente.sensores.domain.model.valueobjects.TipoSensor;
import java.time.LocalDateTime;
import java.util.List;

public record GetSensorHistoryQuery(
        List<TipoSensor> tiposSensor, // Puede pedir el historial de varios sensores a la vez
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {}
