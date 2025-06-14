package com.tomateritmo.arqemergente.anomalias.application.internal.commandservices;

import com.tomateritmo.arqemergente.anomalias.domain.model.aggregates.Anomalia;
import com.tomateritmo.arqemergente.anomalias.domain.model.commands.CreateAnomaliaCommand;
import com.tomateritmo.arqemergente.anomalias.domain.services.AnomaliaCommandService;
import com.tomateritmo.arqemergente.anomalias.infrastructure.persistence.jpa.repositories.AnomaliaRepository;
import com.tomateritmo.arqemergente.cultivos.domain.model.queries.GetCultivoByIdQuery;
import com.tomateritmo.arqemergente.cultivos.domain.services.CultivoQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnomaliaCommandServiceImpl implements AnomaliaCommandService {

    private final AnomaliaRepository anomaliaRepository;
    private final CultivoQueryService cultivoQueryService; // Spring lo inyectará sin problema

    /**
     * El contenedor de Spring detectará automáticamente la implementación de CultivoQueryService
     * (que es CultivoQueryServiceImpl) y la inyectará aquí.
     */
    public AnomaliaCommandServiceImpl(AnomaliaRepository anomaliaRepository, CultivoQueryService cultivoQueryService) {
        this.anomaliaRepository = anomaliaRepository;
        this.cultivoQueryService = cultivoQueryService;
    }

    @Override
    public Optional<Anomalia> handle(CreateAnomaliaCommand command) {
        // Validación para asegurar que el Cultivo existe antes de crear la Anomalía.
        // Se utiliza el Query Service del Bounded Context 'cultivos'.
        cultivoQueryService.handle(new GetCultivoByIdQuery(command.cultivoId()))
                .orElseThrow(() -> new IllegalArgumentException("El cultivo con ID " + command.cultivoId() + " no existe."));

        // Si la línea anterior no lanza una excepción, el cultivo es válido.
        // Procedemos a crear y guardar la anomalía.
        var anomalia = new Anomalia(command);
        anomaliaRepository.save(anomalia);

        return Optional.of(anomalia);
    }
}
