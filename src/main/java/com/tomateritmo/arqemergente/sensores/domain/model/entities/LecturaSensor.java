package com.tomateritmo.arqemergente.sensores.domain.model.entities;

import com.tomateritmo.arqemergente.sensores.domain.model.commands.RegistrarLecturaSensorCommand;
import com.tomateritmo.arqemergente.sensores.domain.model.valueobjects.TipoSensor;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class LecturaSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "tipo_sensor")
    private TipoSensor tipoSensor;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String sector; // A qué zona del cultivo pertenece la lectura

    public LecturaSensor() {}

    public LecturaSensor(RegistrarLecturaSensorCommand command) {
        this.tipoSensor = TipoSensor.valueOf(command.tipoSensor());
        this.valor = command.valor();
        this.timestamp = command.timestamp() != null ? command.timestamp() : LocalDateTime.now();
        this.sector = command.sector();
    }
}
