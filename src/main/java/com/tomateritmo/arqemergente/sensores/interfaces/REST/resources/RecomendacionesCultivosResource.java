package com.tomateritmo.arqemergente.sensores.interfaces.REST.resources;

import java.util.List;

public record RecomendacionesCultivosResource(
    boolean activarRiego,
    boolean abrirCompuertasLuz,
    boolean activarVentilacion,
    List<String> recomendaciones,
    String nivelHumedad,  // "BAJO", "NORMAL", "ALTO"
    String nivelLuminosidad, // "BAJO", "NORMAL", "ALTO"
    String nivelTemperatura // "BAJO", "NORMAL", "ALTO"
) {}
