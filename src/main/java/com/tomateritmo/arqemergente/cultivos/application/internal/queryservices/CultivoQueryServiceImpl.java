package com.tomateritmo.arqemergente.cultivos.application.internal.queryservices;

import com.tomateritmo.arqemergente.cultivos.domain.model.aggregates.Cultivo;
import com.tomateritmo.arqemergente.cultivos.domain.model.queries.*;
import com.tomateritmo.arqemergente.cultivos.domain.services.CultivoQueryService;
import com.tomateritmo.arqemergente.cultivos.infrastructure.persistence.jpa.repositories.CultivoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CultivoQueryServiceImpl implements CultivoQueryService {
    private final CultivoRepository cultivoRepository;

    public CultivoQueryServiceImpl(CultivoRepository cultivoRepository) {
        this.cultivoRepository = cultivoRepository;
    }

    @Override
    public List<Cultivo> handle(GetAllCultivosQuery query) {
        return cultivoRepository.findAll();
    }

    @Override
    public Optional<Cultivo> handle(GetCultivoByIdQuery query) {
        return cultivoRepository.findById(query.cultivoId());
    }

    @Override
    public List<Cultivo> handle(GetCultivosByUserIdQuery query) {
        return cultivoRepository.findByUserId(query.userId());
    }
}
