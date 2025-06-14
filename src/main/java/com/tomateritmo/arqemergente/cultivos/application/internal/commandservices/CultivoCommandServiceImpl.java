package com.tomateritmo.arqemergente.cultivos.application.internal.commandservices;

import com.tomateritmo.arqemergente.cultivos.domain.model.aggregates.Cultivo;
import com.tomateritmo.arqemergente.cultivos.domain.model.commands.*;
import com.tomateritmo.arqemergente.cultivos.domain.services.CultivoCommandService;
import com.tomateritmo.arqemergente.cultivos.infrastructure.persistence.jpa.repositories.CultivoRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CultivoCommandServiceImpl implements CultivoCommandService {
    private final CultivoRepository cultivoRepository;

    public CultivoCommandServiceImpl(CultivoRepository cultivoRepository) {
        this.cultivoRepository = cultivoRepository;
    }

    @Override
    public Optional<Cultivo> handle(CreateCultivoCommand command) {
        var cultivo = new Cultivo(command);
        cultivoRepository.save(cultivo);
        return Optional.of(cultivo);
    }

    @Override
    public Optional<Cultivo> handle(Long cultivoId, UpdateCultivoCommand command) {
        return cultivoRepository.findById(cultivoId).map(cultivo -> {
            cultivo.update(command);
            cultivoRepository.save(cultivo);
            return cultivo;
        });
    }

    @Override
    public void handle(DeleteCultivoCommand command) {
        if (!cultivoRepository.existsById(command.cultivoId())) {
            throw new IllegalArgumentException("Cultivo con ID " + command.cultivoId() + " no encontrado.");
        }
        cultivoRepository.deleteById(command.cultivoId());
    }
}
