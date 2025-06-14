package com.tomateritmo.arqemergente.cultivos.domain.model.commands;

import java.time.LocalDate;
public record UpdateCultivoCommand(String nombre, String sector, String faseFenologica, LocalDate fechaPlantacion) {}
