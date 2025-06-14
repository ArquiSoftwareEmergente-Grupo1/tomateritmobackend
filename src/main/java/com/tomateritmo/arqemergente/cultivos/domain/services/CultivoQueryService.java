package com.tomateritmo.arqemergente.cultivos.domain.services;

import com.tomateritmo.arqemergente.cultivos.domain.model.aggregates.Cultivo;
import com.tomateritmo.arqemergente.cultivos.domain.model.queries.*;
import java.util.List;
import java.util.Optional;
public interface CultivoQueryService {
    List<Cultivo> handle(GetAllCultivosQuery query);
    Optional<Cultivo> handle(GetCultivoByIdQuery query);
}
