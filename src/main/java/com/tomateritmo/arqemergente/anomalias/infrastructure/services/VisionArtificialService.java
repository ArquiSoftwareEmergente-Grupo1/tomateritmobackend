package com.tomateritmo.arqemergente.anomalias.infrastructure.services;

import com.tomateritmo.arqemergente.anomalias.domain.model.commands.CreateAnomaliaCommand;
import com.tomateritmo.arqemergente.anomalias.domain.services.AnomaliaCommandService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class VisionArtificialService {

    @Value("${vision.artificial.url:http://localhost:5000}")
    private String visionArtificialUrl;

    private final RestTemplate restTemplate;
    private final AnomaliaCommandService anomaliaCommandService;

    public VisionArtificialService(RestTemplate restTemplate, AnomaliaCommandService anomaliaCommandService) {
        this.restTemplate = restTemplate;
        this.anomaliaCommandService = anomaliaCommandService;
    }

    public Map<String, Object> analizarImagen(MultipartFile imagen, String cultivoId) {
        try {
            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Crear el body de la petición
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", imagen.getResource());

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Hacer la llamada a la Azure Function
            String url = visionArtificialUrl + "/predict";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            // Procesar la respuesta
            Map<String, Object> resultado = new HashMap<>();
            if (response.getBody() != null) {
                String anomaliaClass = (String) response.getBody().get("class");
                Double confidence = ((Number) response.getBody().get("confidence")).doubleValue();
                
                resultado.put("id", java.util.UUID.randomUUID().toString());
                resultado.put("class", anomaliaClass);
                resultado.put("confidence", confidence);
                resultado.put("cultivoId", cultivoId);
                resultado.put("fechaAnalisis", java.time.LocalDateTime.now().toString());

                // Solo guardar en BD si se detecta una anomalía (no es "healthy")
                if (cultivoId != null && !"healthy".equalsIgnoreCase(anomaliaClass) && !"Healthy".equalsIgnoreCase(anomaliaClass)) {
                    try {
                        Long cultivoIdLong = Long.parseLong(cultivoId);
                        String descripcion = generateDescripcionByAnomalia(anomaliaClass);
                        
                        CreateAnomaliaCommand command = new CreateAnomaliaCommand(
                            cultivoIdLong,
                            anomaliaClass,
                            descripcion,
                            "imagen_" + resultado.get("id"), // Placeholder para la URL de la imagen
                            confidence
                        );
                        
                        var anomaliaSaved = anomaliaCommandService.handle(command);
                        if (anomaliaSaved.isPresent()) {
                            resultado.put("anomaliaId", anomaliaSaved.get().getId());
                        }
                    } catch (NumberFormatException e) {
                        // Log error but continue
                        System.err.println("Error parsing cultivoId: " + cultivoId);
                    }
                }
            }

            return resultado;

        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con el servicio de visión artificial: " + e.getMessage());
        }
    }

    private String generateDescripcionByAnomalia(String anomaliaClass) {
        return switch (anomaliaClass) {
            case "Bacterial_spot" -> "Mancha bacteriana detectada en las hojas. Requiere tratamiento con fungicida de cobre.";
            case "Early_blight" -> "Tizón temprano detectado. Se recomienda mejorar la ventilación y aplicar fungicida sistémico.";
            case "Late_blight" -> "Tizón tardío detectado. Eliminar plantas infectadas y controlar humedad.";
            case "Leaf_Mold" -> "Moho de las hojas detectado. Usar variedades resistentes y controlar humedad.";
            case "Septoria_leaf_spot" -> "Mancha foliar por Septoria. Eliminar hojas afectadas y aplicar fungicida.";
            case "Spider_mites" -> "Ácaros detectados. Usar control biológico y mejorar condiciones ambientales.";
            case "Target_Spot" -> "Mancha objetivo detectada. Eliminar hojas viejas y controlar riego.";
            case "Tomato_Yellow_Leaf_Curl_Virus" -> "Virus del rizado amarillo detectado. Controlar mosca blanca.";
            case "Tomato_mosaic_virus" -> "Virus del mosaico del tomate detectado. Desinfectar herramientas.";
            default -> "Anomalía detectada que requiere atención.";
        };
    }
}
