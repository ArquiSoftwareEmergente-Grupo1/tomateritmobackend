package com.tomateritmo.arqemergente.cultivos.domain.services;

import com.tomateritmo.arqemergente.cultivos.domain.model.aggregates.Cultivo;
import com.tomateritmo.arqemergente.cultivos.domain.model.commands.*;
import java.util.Optional;
public interface CultivoCommandService {
    Optional<Cultivo> handle(CreateCultivoCommand command);
    Optional<Cultivo> handle(Long cultivoId, UpdateCultivoCommand command);
    void handle(DeleteCultivoCommand command);
}

