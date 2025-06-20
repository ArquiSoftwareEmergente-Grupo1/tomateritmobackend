package com.tomateritmo.arqemergente.sensores.domain.model.queries;


public record GetLatestLecturasQuery(int limit) {
    public GetLatestLecturasQuery {
        if (limit <= 0) {
            throw new IllegalArgumentException("El límite debe ser un número positivo.");
        }
    }
}
