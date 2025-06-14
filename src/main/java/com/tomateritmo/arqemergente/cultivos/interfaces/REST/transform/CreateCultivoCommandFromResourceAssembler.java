package com.tomateritmo.arqemergente.cultivos.interfaces.REST.transform;

import com.tomateritmo.arqemergente.cultivos.domain.model.commands.CreateCultivoCommand;
import com.tomateritmo.arqemergente.cultivos.interfaces.REST.resources.CreateCultivoResource;
public class CreateCultivoCommandFromResourceAssembler {
    public static CreateCultivoCommand toCommandFromResource(CreateCultivoResource resource) {
        return new CreateCultivoCommand(
                resource.nombre(),
                resource.sector(),
                resource.faseFenologica(),
                resource.fechaPlantacion());
    }
}


