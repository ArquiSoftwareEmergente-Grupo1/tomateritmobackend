package com.tomateritmo.arqemergente.anomalias.interfaces.REST.transform;

import com.tomateritmo.arqemergente.anomalias.domain.model.aggregates.Anomalia;
import com.tomateritmo.arqemergente.anomalias.interfaces.REST.resources.AnomaliaResource;

public class AnomaliaResourceFromEntityAssembler {
    public static AnomaliaResource toResourceFromEntity(Anomalia entity) {
        return new AnomaliaResource(
                entity.getId(),
                entity.getCultivoId(),
                entity.getNombreAnomalia(),
                entity.getDescripcion(),
                entity.getImageUrl(),
                entity.getConfianza(),
                entity.getFechaDeteccion()
        );
    }
}
