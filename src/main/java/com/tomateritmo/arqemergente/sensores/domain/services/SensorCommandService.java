package com.tomateritmo.arqemergente.sensores.domain.services;

import com.tomateritmo.arqemergente.sensores.domain.model.commands.RegistrarLecturaSensorCommand;
import com.tomateritmo.arqemergente.sensores.domain.model.entities.LecturaSensor;
import java.util.Optional;

public interface SensorCommandService {
    Optional<LecturaSensor> handle(RegistrarLecturaSensorCommand command);
}
