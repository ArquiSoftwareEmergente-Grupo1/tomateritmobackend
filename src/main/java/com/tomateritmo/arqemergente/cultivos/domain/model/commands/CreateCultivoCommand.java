package com.tomateritmo.arqemergente.cultivos.domain.model.commands;

import java.time.LocalDate;
public record CreateCultivoCommand(Long userId, String nombre, String sector, String faseFenologica, LocalDate fechaPlantacion) {}
