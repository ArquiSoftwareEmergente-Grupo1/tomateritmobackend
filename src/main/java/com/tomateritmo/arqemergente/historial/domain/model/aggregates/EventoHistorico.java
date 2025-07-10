package com.tomateritmo.arqemergente.historial.domain.model.aggregates;

import com.tomateritmo.arqemergente.historial.domain.model.commands.CreateEventoHistoricoCommand;
import com.tomateritmo.arqemergente.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_trail")
@Getter
public class EventoHistorico extends AuditableAbstractAggregateRoot<EventoHistorico> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String evento;

    @Column(name = "cultivo_name")
    private String cultivoName;

    @Column(length = 2000)
    private String detalles;

    public EventoHistorico() {
    }

    public EventoHistorico(CreateEventoHistoricoCommand command) {
        this.fecha = command.fecha();
        this.evento = command.evento();
        this.cultivoName = command.cultivoName();
        this.detalles = command.detalles();
    }
}
