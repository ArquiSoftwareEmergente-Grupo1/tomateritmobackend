package com.tomateritmo.arqemergente.sensores.interfaces.REST.transform;

import com.tomateritmo.arqemergente.sensores.domain.model.commands.RegistrarLecturaSensorCommand;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.RegistrarLecturaResource;

public class RegistrarLecturaCommandFromResourceAssembler {
    public static RegistrarLecturaSensorCommand toCommandFromResource(RegistrarLecturaResource resource) {
        return new RegistrarLecturaSensorCommand(
                resource.tipoSensor(),
                resource.valor(),
                resource.timestamp(),
                resource.sector()
        );
    }
}
