package com.tomateritmo.arqemergente.historial.interfaces.REST.transform;

import com.tomateritmo.arqemergente.historial.domain.model.queries.GetEventosHistoricosByFilterQuery;
import com.tomateritmo.arqemergente.historial.interfaces.REST.resources.FiltroHistorialResource;

public class GetEventosHistoricosByFilterQueryFromResourceAssembler {

    public static GetEventosHistoricosByFilterQuery toQueryFromResource(FiltroHistorialResource resource) {
        return new GetEventosHistoricosByFilterQuery(
                resource.tipo(),
                resource.zona(),
                resource.fechaInicio(),
                resource.fechaFin()
        );
    }
}
