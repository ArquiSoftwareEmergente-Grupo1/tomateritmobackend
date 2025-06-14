package com.tomateritmo.arqemergente.anomalias.domain.services;

import com.tomateritmo.arqemergente.anomalias.domain.model.aggregates.Anomalia;
import com.tomateritmo.arqemergente.anomalias.domain.model.commands.CreateAnomaliaCommand;

import java.util.Optional;

public interface AnomaliaCommandService {
    Optional<Anomalia> handle(CreateAnomaliaCommand command);
}
