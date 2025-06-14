package com.tomateritmo.arqemergente.anomalias.interfaces.REST.resources;

public record CreateAnomaliaResource(
        Long cultivoId,
        String nombreAnomalia,
        String descripcion,
        String imageUrl, // La URL de la imagen después de haberla subido a un S3, Cloudinary, etc.
        Double confianza
) {}
