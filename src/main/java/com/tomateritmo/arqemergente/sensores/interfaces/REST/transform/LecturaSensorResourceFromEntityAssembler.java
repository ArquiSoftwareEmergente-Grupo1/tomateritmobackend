package com.tomateritmo.arqemergente.sensores.interfaces.REST.transform;

import com.tomateritmo.arqemergente.sensores.domain.model.entities.LecturaSensor;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.LecturaSensorResource;

public class LecturaSensorResourceFromEntityAssembler {
    public static LecturaSensorResource toResourceFromEntity(LecturaSensor entity) {
        return new LecturaSensorResource(
                entity.getId(),
                entity.getTipoSensor().name(),
                entity.getValor(),
                entity.getTimestamp(),
                entity.getSector()
        );
    }
}
