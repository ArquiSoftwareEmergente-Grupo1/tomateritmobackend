package com.tomateritmo.arqemergente.anomalias.interfaces.REST.transform;

import com.tomateritmo.arqemergente.anomalias.domain.model.commands.CreateAnomaliaCommand;
import com.tomateritmo.arqemergente.anomalias.interfaces.REST.resources.CreateAnomaliaResource;

public class CreateAnomaliaCommandFromResourceAssembler {
    public static CreateAnomaliaCommand toCommandFromResource(CreateAnomaliaResource resource) {
        return new CreateAnomaliaCommand(
                resource.cultivoId(),
                resource.nombreAnomalia(),
                resource.descripcion(),
                resource.imageUrl(),
                resource.confianza()
        );
    }
}
