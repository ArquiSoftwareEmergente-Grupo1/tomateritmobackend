package com.tomateritmo.arqemergente.analisis.interfaces.REST.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AnalisisResultadoDTO {
    private Long id;
    private String imagen;
    private String diagnostico;
    private String anomalia;
    private Double confianza;
    private List<String> recomendaciones;
    private String cultivoId;
    private LocalDateTime fechaAnalisis;
    private Boolean confirmado;
    private String imagenPath;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // Constructor por defecto
    public AnalisisResultadoDTO() {}

    // Constructor completo
    public AnalisisResultadoDTO(Long id, String imagen, String diagnostico, String anomalia, 
                               Double confianza, List<String> recomendaciones, String cultivoId, 
                               LocalDateTime fechaAnalisis, Boolean confirmado, String imagenPath,
                               LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.imagen = imagen;
        this.diagnostico = diagnostico;
        this.anomalia = anomalia;
        this.confianza = confianza;
        this.recomendaciones = recomendaciones;
        this.cultivoId = cultivoId;
        this.fechaAnalisis = fechaAnalisis;
        this.confirmado = confirmado;
        this.imagenPath = imagenPath;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
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
