package com.tomateritmo.arqemergente.analisis.domain.model.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas_visuales")
public class AlertaVisual {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "titulo", length = 200, nullable = false)
    private String titulo;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "severidad", length = 50)
    private String severidad; // baja, media, alta, critica
    
    @Column(name = "estado", length = 50)
    private String estado; // activa, resuelta, ignorada
    
    @Column(name = "cultivo_id")
    private String cultivoId;
    
    @Column(name = "anomalia_detectada", length = 100)
    private String anomaliaDetectada;
    
    @Column(name = "fecha_deteccion")
    private LocalDateTime fechaDeteccion;
    
    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // Constructor por defecto
    public AlertaVisual() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.fechaDeteccion = LocalDateTime.now();
        this.estado = "activa";
    }

    // Constructor con parámetros principales
    public AlertaVisual(String titulo, String descripcion, String severidad, String cultivoId, String anomaliaDetectada) {
        this();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.severidad = severidad;
        this.cultivoId = cultivoId;
        this.anomaliaDetectada = anomaliaDetectada;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCultivoId() {
        return cultivoId;
    }

    public void setCultivoId(String cultivoId) {
        this.cultivoId = cultivoId;
    }

    public String getAnomaliaDetectada() {
        return anomaliaDetectada;
    }

    public void setAnomaliaDetectada(String anomaliaDetectada) {
        this.anomaliaDetectada = anomaliaDetectada;
    }

    public LocalDateTime getFechaDeteccion() {
        return fechaDeteccion;
    }

    public void setFechaDeteccion(LocalDateTime fechaDeteccion) {
        this.fechaDeteccion = fechaDeteccion;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
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
