package com.tomateritmo.arqemergente.analisis.domain.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "analisis_resultados")
public class AnalisisResultado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "imagen_url", length = 500)
    private String imagen;
    
    @Column(name = "diagnostico", length = 200)
    private String diagnostico;
    
    @Column(name = "anomalia", length = 100)
    private String anomalia;
    
    @Column(name = "confianza")
    private Double confianza;
    
    @ElementCollection
    @CollectionTable(name = "analisis_recomendaciones", joinColumns = @JoinColumn(name = "analisis_id"))
    @Column(name = "recomendacion", length = 300)
    private List<String> recomendaciones;
    
    @Column(name = "cultivo_id")
    private String cultivoId;
    
    @Column(name = "fecha_analisis")
    private LocalDateTime fechaAnalisis;
    
    @Column(name = "confirmado")
    private Boolean confirmado;
    
    @Column(name = "imagen_path", length = 300)
    private String imagenPath;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Constructor por defecto
    public AnalisisResultado() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.confirmado = false;
    }

    // Constructor con parámetros principales
    public AnalisisResultado(String imagen, String diagnostico, String anomalia, 
                           Double confianza, List<String> recomendaciones, String cultivoId) {
        this();
        this.imagen = imagen;
        this.diagnostico = diagnostico;
        this.anomalia = anomalia;
        this.confianza = confianza;
        this.recomendaciones = recomendaciones;
        this.cultivoId = cultivoId;
        this.fechaAnalisis = LocalDateTime.now();
    }

    // Método para actualizar timestamp antes de modificar
    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getAnomalia() {
        return anomalia;
    }

    public void setAnomalia(String anomalia) {
        this.anomalia = anomalia;
    }

    public Double getConfianza() {
        return confianza;
    }

    public void setConfianza(Double confianza) {
        this.confianza = confianza;
    }

    public List<String> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<String> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getCultivoId() {
        return cultivoId;
    }

    public void setCultivoId(String cultivoId) {
        this.cultivoId = cultivoId;
    }

    public LocalDateTime getFechaAnalisis() {
        return fechaAnalisis;
    }

    public void setFechaAnalisis(LocalDateTime fechaAnalisis) {
        this.fechaAnalisis = fechaAnalisis;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(Boolean confirmado) {
        this.confirmado = confirmado;
    }

    public String getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
