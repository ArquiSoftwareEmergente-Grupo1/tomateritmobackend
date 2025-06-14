package com.tomateritmo.arqemergente.cultivos.domain.model.commands;

import java.time.LocalDate;
public record CreateCultivoCommand(String nombre, String sector, String faseFenologica, LocalDate fechaPlantacion) {}
