package com.tomateritmo.arqemergente.cultivos.domain.model.aggregates;

import com.tomateritmo.arqemergente.cultivos.domain.model.commands.CreateCultivoCommand;
import com.tomateritmo.arqemergente.cultivos.domain.model.commands.UpdateCultivoCommand;
import com.tomateritmo.arqemergente.cultivos.domain.model.valueobjects.FaseFenologica;
import com.tomateritmo.arqemergente.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Getter
public class Cultivo extends AuditableAbstractAggregateRoot<Cultivo> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String sector;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FaseFenologica faseFenologica;

    @Column(nullable = false)
    private LocalDate fechaPlantacion;

    private LocalDate fechaCosechaEstimada;

    private Integer progreso; // En porcentaje

    public Cultivo() {}

    public Cultivo(CreateCultivoCommand command) {
        this.userId = command.userId();
        this.nombre = command.nombre();
        this.sector = command.sector();
        this.faseFenologica = FaseFenologica.valueOf(command.faseFenologica());
        this.fechaPlantacion = command.fechaPlantacion();
        // Lógica de negocio para calcular valores iniciales
        this.progreso = 10; // Ejemplo
        this.fechaCosechaEstimada = command.fechaPlantacion().plusDays(194); // Ejemplo
    }

    public void update(UpdateCultivoCommand command) {
        this.nombre = command.nombre();
        this.sector = command.sector();
        this.faseFenologica = FaseFenologica.valueOf(command.faseFenologica());
        this.fechaPlantacion = command.fechaPlantacion();
    }
}
