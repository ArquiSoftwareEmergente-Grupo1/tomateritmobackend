package com.tomateritmo.arqemergente.cultivos.interfaces.REST.transform;

import com.tomateritmo.arqemergente.cultivos.domain.model.commands.UpdateCultivoCommand;
import com.tomateritmo.arqemergente.cultivos.interfaces.REST.resources.UpdateCultivoResource;
public class UpdateCultivoCommandFromResourceAssembler {
    public static UpdateCultivoCommand toCommandFromResource(UpdateCultivoResource resource) {
        return new UpdateCultivoCommand(
                resource.nombre(),
                resource.sector(),
                resource.faseFenologica(),
                resource.fechaPlantacion());
    }
}
