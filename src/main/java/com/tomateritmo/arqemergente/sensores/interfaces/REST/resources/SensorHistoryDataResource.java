package com.tomateritmo.arqemergente.sensores.interfaces.REST.resources;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

// Formato que se puede usar directamente con librerías de gráficos
public record SensorHistoryDataResource(
        Map<String, List<DataPoint>> series
) {
    public record DataPoint(LocalDateTime x, Double y) {}
}
