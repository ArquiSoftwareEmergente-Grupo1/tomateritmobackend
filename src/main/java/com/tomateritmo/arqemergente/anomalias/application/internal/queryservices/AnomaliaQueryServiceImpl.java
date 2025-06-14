package com.tomateritmo.arqemergente.anomalias.application.internal.queryservices;

import com.tomateritmo.arqemergente.anomalias.domain.model.aggregates.Anomalia;
import com.tomateritmo.arqemergente.anomalias.domain.model.queries.GetAllAnomaliasByCultivoIdQuery;
import com.tomateritmo.arqemergente.anomalias.domain.model.queries.GetAllAnomaliasQuery;
import com.tomateritmo.arqemergente.anomalias.domain.model.queries.GetAnomaliaByIdQuery;
import com.tomateritmo.arqemergente.anomalias.domain.services.AnomaliaQueryService;
import com.tomateritmo.arqemergente.anomalias.infrastructure.persistence.jpa.repositories.AnomaliaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnomaliaQueryServiceImpl implements AnomaliaQueryService {

    private final AnomaliaRepository anomaliaRepository;

    public AnomaliaQueryServiceImpl(AnomaliaRepository anomaliaRepository) {
        this.anomaliaRepository = anomaliaRepository;
    }

    @Override
    public Optional<Anomalia> handle(GetAnomaliaByIdQuery query) {
        return anomaliaRepository.findById(query.anomaliaId());
    }

    @Override
    public List<Anomalia> handle(GetAllAnomaliasQuery query) {
        return anomaliaRepository.findAll();
    }

    @Override
    public List<Anomalia> handle(GetAllAnomaliasByCultivoIdQuery query) {
        return anomaliaRepository.findByCultivoId(query.cultivoId());
    }
}
