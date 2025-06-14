package com.tomateritmo.arqemergente.sensores.application.internal.commandservices;

import com.tomateritmo.arqemergente.sensores.domain.model.commands.RegistrarLecturaSensorCommand;
import com.tomateritmo.arqemergente.sensores.domain.model.entities.LecturaSensor;
import com.tomateritmo.arqemergente.sensores.domain.services.SensorCommandService;
import com.tomateritmo.arqemergente.sensores.infrastructure.persistence.jpa.repositories.LecturaSensorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorCommandServiceImpl implements SensorCommandService {

    private final LecturaSensorRepository lecturaSensorRepository;

    public SensorCommandServiceImpl(LecturaSensorRepository lecturaSensorRepository) {
        this.lecturaSensorRepository = lecturaSensorRepository;
    }

    @Override
    public Optional<LecturaSensor> handle(RegistrarLecturaSensorCommand command) {
        var lectura = new LecturaSensor(command);
        lecturaSensorRepository.save(lectura);
        return Optional.of(lectura);
    }
}
