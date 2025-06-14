package com.tomateritmo.arqemergente.cultivos.interfaces.REST.resources;

import java.time.LocalDate;
public record CreateCultivoResource(String nombre, String sector, String faseFenologica, LocalDate fechaPlantacion) {}
