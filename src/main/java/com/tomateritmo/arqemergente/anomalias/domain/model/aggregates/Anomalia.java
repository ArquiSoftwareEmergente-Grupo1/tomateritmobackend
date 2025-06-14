package com.tomateritmo.arqemergente.anomalias.domain.model.aggregates;

import com.tomateritmo.arqemergente.anomalias.domain.model.commands.CreateAnomaliaCommand;
import com.tomateritmo.arqemergente.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Anomalia extends AuditableAbstractAggregateRoot<Anomalia> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Podría ser un FK a la tabla de Cultivos si ambos BC comparten DB,
    // o simplemente un ID si son microservicios separados.
    @Column(nullable = false, name = "cultivo_id")
    private Long cultivoId;

    @Column(nullable = false, name = "nombre_anomalia")
    private String nombreAnomalia;

    @Lob // Para textos largos
    private String descripcion;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    // La confianza del diagnóstico (ej. 0.91 para 91%)
    @Column(nullable = false)
    private Double confianza;

    @Column(nullable = false, name = "fecha_deteccion")
    private LocalDateTime fechaDeteccion;

    // Constructor por defecto requerido por JPA
    public Anomalia() {}

    /**
     * Constructor para crear una nueva Anomalia a partir de un comando.
     * Encapsula la lógica de creación.
     */
    public Anomalia(CreateAnomaliaCommand command) {
        this.cultivoId = command.cultivoId();
        this.nombreAnomalia = command.nombreAnomalia();
        this.descripcion = command.descripcion();
        this.imageUrl = command.imageUrl();
        this.confianza = command.confianza();
        this.fechaDeteccion = LocalDateTime.now(); // La fecha se establece en el momento de la creación
    }
}
