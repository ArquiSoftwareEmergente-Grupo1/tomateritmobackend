package com.tomateritmo.arqemergente.anomalias.domain.services;

import com.tomateritmo.arqemergente.anomalias.domain.model.aggregates.Anomalia;
import com.tomateritmo.arqemergente.anomalias.domain.model.queries.GetAllAnomaliasByCultivoIdQuery;
import com.tomateritmo.arqemergente.anomalias.domain.model.queries.GetAllAnomaliasQuery;
import com.tomateritmo.arqemergente.anomalias.domain.model.queries.GetAnomaliaByIdQuery;

import java.util.List;
import java.util.Optional;

public interface AnomaliaQueryService {
    Optional<Anomalia> handle(GetAnomaliaByIdQuery query);
    List<Anomalia> handle(GetAllAnomaliasQuery query);
    List<Anomalia> handle(GetAllAnomaliasByCultivoIdQuery query);
}
