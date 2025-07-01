package com.tomateritmo.arqemergente.cultivos.interfaces.REST.resources;

import java.time.LocalDate;
public record CultivoResource(Long id, Long userId, String nombre, String sector, String faseFenologica, LocalDate fechaPlantacion, LocalDate fechaCosechaEstimada, Integer progreso) {}
