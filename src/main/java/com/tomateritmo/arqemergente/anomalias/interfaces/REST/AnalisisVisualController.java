package com.tomateritmo.arqemergente.anomalias.interfaces.REST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/legacy", produces = APPLICATION_JSON_VALUE)
public class AnalisisVisualController {

    @GetMapping("/analisisVisuales")
    public ResponseEntity<List<Map<String, Object>>> getAnalisisVisuales() {
        List<Map<String, Object>> resultados = new ArrayList<>();
        
        // Datos de ejemplo para análisis resultados
        Map<String, Object> resultado1 = new HashMap<>();
        resultado1.put("id", "1");
        resultado1.put("imagen", "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iIzRhZjUwYSIvPjx0ZXh0IHg9IjUwJSIgeT0iNTAlIiBkb21pbmFudC1iYXNlbGluZT0ibWlkZGxlIiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmaWxsPSJ3aGl0ZSIgZm9udC1zaXplPSIxNHB4Ij5TYWx1ZGFibGU8L3RleHQ+PC9zdmc+");
        resultado1.put("diagnostico", "Cultivo saludable");
        resultado1.put("anomalia", "healthy");
        resultado1.put("confianza", 0.95);
        resultado1.put("recomendaciones", List.of("Mantener las condiciones actuales", "Continuar con el monitoreo regular"));
        resultado1.put("cultivoId", "1");
        resultado1.put("fechaAnalisis", LocalDateTime.now().minusDays(1).toString());
        resultado1.put("confirmado", true);
        
        Map<String, Object> resultado2 = new HashMap<>();
        resultado2.put("id", "2");
        resultado2.put("imagen", "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI2Y1OWUwYiIvPjx0ZXh0IHg9IjUwJSIgeT0iNTAlIiBkb21pbmFudC1iYXNlbGluZT0ibWlkZGxlIiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmaWxsPSJ3aGl0ZSIgZm9udC1zaXplPSIxMnB4Ij5UaXrDs24gdGVtcHJhbm88L3RleHQ+PC9zdmc+");
        resultado2.put("diagnostico", "Anomalía detectada");
        resultado2.put("anomalia", "Early_blight");
        resultado2.put("confianza", 0.87);
        resultado2.put("recomendaciones", List.of("Mejorar ventilación del cultivo", "Aplicar fungicida sistémico", "Rotar cultivos"));
        resultado2.put("cultivoId", "1");
        resultado2.put("fechaAnalisis", LocalDateTime.now().minusDays(2).toString());
        resultado2.put("confirmado", false);
        
        Map<String, Object> resultado3 = new HashMap<>();
        resultado3.put("id", "3");
        resultado3.put("imagen", "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI2Y1OWUwYiIvPjx0ZXh0IHg9IjUwJSIgeT0iNTAlIiBkb21pbmFudC1iYXNlbGluZT0ibWlkZGxlIiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmaWxsPSJ3aGl0ZSIgZm9udC1zaXplPSIxMnB4Ij5CYWN0ZXJpYWw8L3RleHQ+PC9zdmc+");
        resultado3.put("diagnostico", "Anomalía detectada");
        resultado3.put("anomalia", "Bacterial_spot");
        resultado3.put("confianza", 0.78);
        resultado3.put("recomendaciones", List.of("Aplicar fungicida de cobre", "Evitar riego por aspersión", "Eliminar hojas afectadas"));
        resultado3.put("cultivoId", "2");
        resultado3.put("fechaAnalisis", LocalDateTime.now().minusDays(3).toString());
        resultado3.put("confirmado", true);
        
        Map<String, Object> resultado4 = new HashMap<>();
        resultado4.put("id", "4");
        resultado4.put("imagen", "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iIzRhZjUwYSIvPjx0ZXh0IHg9IjUwJSIgeT0iNTAlIiBkb21pbmFudC1iYXNlbGluZT0ibWlkZGxlIiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmaWxsPSJ3aGl0ZSIgZm9udC1zaXplPSIxNHB4Ij5TYWx1ZGFibGU8L3RleHQ+PC9zdmc+");
        resultado4.put("diagnostico", "Cultivo saludable");
        resultado4.put("anomalia", "healthy");
        resultado4.put("confianza", 0.91);
        resultado4.put("recomendaciones", List.of("Mantener las condiciones actuales", "Continuar con el monitoreo regular"));
        resultado4.put("cultivoId", "2");
        resultado4.put("fechaAnalisis", LocalDateTime.now().minusHours(6).toString());
        resultado4.put("confirmado", false);
        
        Map<String, Object> resultado5 = new HashMap<>();
        resultado5.put("id", "5");
        resultado5.put("imagen", "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI2VmNDQ0NCIvPjx0ZXh0IHg9IjUwJSIgeT0iNTAlIiBkb21pbmFudC1iYXNlbGluZT0ibWlkZGxlIiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmaWxsPSJ3aGl0ZSIgZm9udC1zaXplPSIxMnB4Ij5UaXrDs24gdGFyZMOtbzwvdGV4dD48L3N2Zz4=");
        resultado5.put("diagnostico", "Anomalía detectada");
        resultado5.put("anomalia", "Late_blight");
        resultado5.put("confianza", 0.94);
        resultado5.put("recomendaciones", List.of("Aplicar fungicida específico para tizón tardío", "Eliminar plantas infectadas", "Controlar humedad"));
        resultado5.put("cultivoId", "3");
        resultado5.put("fechaAnalisis", LocalDateTime.now().minusHours(12).toString());
        resultado5.put("confirmado", false);
        
        resultados.add(resultado1);
        resultados.add(resultado2);
        resultados.add(resultado3);
        resultados.add(resultado4);
        resultados.add(resultado5);
        
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/anomalias/alertas")
    public ResponseEntity<List<Map<String, Object>>> getAlertasAnomalias(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String severidad) {
        
        List<Map<String, Object>> alertas = new ArrayList<>();
        
        // Datos de ejemplo para alertas visuales
        Map<String, Object> alerta1 = new HashMap<>();
        alerta1.put("id", "1");
        alerta1.put("tipoAnomalia", "Early_blight");
        alerta1.put("severidad", "alta");
        alerta1.put("zonaAfectada", "Hojas inferiores");
        alerta1.put("nivelConfianza", 0.87);
        alerta1.put("fechaDeteccion", LocalDateTime.now().minusDays(1).toString());
        alerta1.put("estado", "activa");
        alerta1.put("cultivoId", "1");
        alerta1.put("descripcion", "Tizón temprano detectado en las hojas inferiores de la planta");
        
        Map<String, Object> alerta2 = new HashMap<>();
        alerta2.put("id", "2");
        alerta2.put("tipoAnomalia", "Bacterial_spot");
        alerta2.put("severidad", "media");
        alerta2.put("zonaAfectada", "Hojas centrales");
        alerta2.put("nivelConfianza", 0.76);
        alerta2.put("fechaDeteccion", LocalDateTime.now().minusDays(3).toString());
        alerta2.put("estado", "resuelta");
        alerta2.put("cultivoId", "2");
        alerta2.put("descripcion", "Mancha bacteriana identificada en las hojas centrales");
        
        Map<String, Object> alerta3 = new HashMap<>();
        alerta3.put("id", "3");
        alerta3.put("tipoAnomalia", "Spider_mites");
        alerta3.put("severidad", "baja");
        alerta3.put("zonaAfectada", "Hojas superiores");
        alerta3.put("nivelConfianza", 0.92);
        alerta3.put("fechaDeteccion", LocalDateTime.now().minusHours(6).toString());
        alerta3.put("estado", "activa");
        alerta3.put("cultivoId", "1");
        alerta3.put("descripcion", "Presencia de ácaros detectada en las hojas superiores");
        
        alertas.add(alerta1);
        alertas.add(alerta2);
        alertas.add(alerta3);
        
        // Filtrar por estado si se proporciona
        if (estado != null && !estado.isEmpty()) {
            alertas = alertas.stream()
                    .filter(a -> estado.equals(a.get("estado")))
                    .toList();
        }
        
        // Filtrar por severidad si se proporciona
        if (severidad != null && !severidad.isEmpty()) {
            alertas = alertas.stream()
                    .filter(a -> severidad.equals(a.get("severidad")))
                    .toList();
        }
        
        return ResponseEntity.ok(alertas);
    }

    @PatchMapping("/analisisResultados/{id}")
    public ResponseEntity<Map<String, Object>> updateAnalisisResultado(
            @PathVariable String id,
            @RequestBody Map<String, Object> request) {
        
        // Simular actualización de análisis resultado
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("id", id);
        resultado.put("confirmado", request.get("confirmado"));
        resultado.put("fechaActualizacion", LocalDateTime.now().toString());
        
        // Simular datos del análisis existente
        resultado.put("diagnostico", "Cultivo saludable");
        resultado.put("anomalia", "healthy");
        resultado.put("confianza", 0.95);
        resultado.put("cultivoId", "1");
        resultado.put("fechaAnalisis", LocalDateTime.now().minusDays(1).toString());
        
        return ResponseEntity.ok(resultado);
    }
}
