package com.tomateritmo.arqemergente.cultivos.interfaces.REST.transform;

import com.tomateritmo.arqemergente.cultivos.domain.model.aggregates.Cultivo;
import com.tomateritmo.arqemergente.cultivos.interfaces.REST.resources.CultivoResource;
public class CultivoResourceFromEntityAssembler {
    public static CultivoResource toResourceFromEntity(Cultivo entity) {
        return new CultivoResource(
                entity.getId(),
                entity.getUserId(),
                entity.getNombre(),
                entity.getSector(),
                entity.getFaseFenologica().name(),
                entity.getFechaPlantacion(),
                entity.getFechaCosechaEstimada(),
                entity.getProgreso());
    }
}
