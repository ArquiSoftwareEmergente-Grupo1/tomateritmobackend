package com.tomateritmo.arqemergente.analisis.interfaces.REST;

import com.tomateritmo.arqemergente.analisis.application.services.AnalisisResultadoService;
import com.tomateritmo.arqemergente.analisis.application.services.AlertaVisualService;
import com.tomateritmo.arqemergente.analisis.domain.model.entities.AnalisisResultado;
import com.tomateritmo.arqemergente.analisis.domain.model.entities.AlertaVisual;
import com.tomateritmo.arqemergente.analisis.interfaces.REST.dto.AnalisisResultadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AnalisisController {

    @Autowired
    private AnalisisResultadoService analisisService;

    @Autowired
    private AlertaVisualService alertaService;

    @GetMapping("/alertasVisuales")
    public ResponseEntity<List<AlertaVisual>> getAlertasVisuales(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String severidad) {
        
        List<AlertaVisual> alertas = alertaService.obtenerAlertasFiltradas(estado, severidad);
        return ResponseEntity.ok(alertas);
    }
    
    @PostMapping("/alertasVisuales")
    public ResponseEntity<AlertaVisual> createAlertaVisual(@RequestBody AlertaVisual alerta) {
        AlertaVisual nuevaAlerta = alertaService.crearAlerta(alerta);
        return ResponseEntity.ok(nuevaAlerta);
    }
    
    @PutMapping("/alertasVisuales/{id}")
    public ResponseEntity<AlertaVisual> updateAlertaVisual(
            @PathVariable Long id, 
            @RequestBody AlertaVisual alerta) {
        
        AlertaVisual alertaActualizada = alertaService.actualizarAlerta(id, alerta);
        return ResponseEntity.ok(alertaActualizada);
    }

    @GetMapping("/analisisResultados")
    public ResponseEntity<List<AnalisisResultadoDTO>> getAnalisisResultados() {
        List<AnalisisResultado> resultados = analisisService.obtenerTodosLosAnalisis();
        List<AnalisisResultadoDTO> resultadosDTO = resultados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultadosDTO);
    }

    @GetMapping("/analisisResultados/{id}")
    public ResponseEntity<AnalisisResultadoDTO> getAnalisisResultadoById(@PathVariable Long id) {
        return analisisService.obtenerAnalisisPorId(id)
                .map(analisis -> ResponseEntity.ok(convertToDTO(analisis)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/analisisResultados")
    public ResponseEntity<AnalisisResultadoDTO> crearAnalisisResultado(@RequestBody Map<String, Object> request) {
        try {
            System.out.println("=== RECIBIENDO ANÁLISIS ===");
            System.out.println("Datos recibidos: " + request);
            
            // Crear entidad desde Map para manejar conversiones flexibles
            AnalisisResultado analisis = new AnalisisResultado();
            
            // Manejar campos String de manera segura
            Object imagenObj = request.get("imagen");
            analisis.setImagen(imagenObj != null ? imagenObj.toString() : null);
            
            Object diagnosticoObj = request.get("diagnostico");
            analisis.setDiagnostico(diagnosticoObj != null ? diagnosticoObj.toString() : null);
            
            Object anomaliaObj = request.get("anomalia");
            analisis.setAnomalia(anomaliaObj != null ? anomaliaObj.toString() : null);
            
            // Manejar confianza
            Object confianzaObj = request.get("confianza");
            if (confianzaObj instanceof Number) {
                analisis.setConfianza(((Number) confianzaObj).doubleValue());
            } else if (confianzaObj instanceof String) {
                analisis.setConfianza(Double.parseDouble((String) confianzaObj));
            }
            
            // Manejar recomendaciones
            @SuppressWarnings("unchecked")
            List<String> recomendaciones = (List<String>) request.get("recomendaciones");
            analisis.setRecomendaciones(recomendaciones);
            
            // Manejar cultivoId (puede venir como String o Integer)
            Object cultivoIdObj = request.get("cultivoId");
            if (cultivoIdObj != null) {
                analisis.setCultivoId(cultivoIdObj.toString());
            }
            
            // Manejar imagenPath de manera segura
            Object imagenPathObj = request.get("imagenPath");
            analisis.setImagenPath(imagenPathObj != null ? imagenPathObj.toString() : null);
            
            // Manejar confirmado
            Object confirmadoObj = request.get("confirmado");
            if (confirmadoObj instanceof Boolean) {
                analisis.setConfirmado((Boolean) confirmadoObj);
            } else {
                analisis.setConfirmado(false); // valor por defecto
            }
            
            // Fecha de análisis - usar la actual si no se proporciona
            Object fechaObj = request.get("fechaAnalisis");
            if (fechaObj instanceof String) {
                try {
                    // Intentar parsear la fecha ISO del frontend
                    String fechaStr = (String) fechaObj;
                    if (fechaStr.endsWith("Z")) {
                        fechaStr = fechaStr.substring(0, fechaStr.length() - 1);
                    }
                    analisis.setFechaAnalisis(LocalDateTime.parse(fechaStr));
                } catch (Exception e) {
                    analisis.setFechaAnalisis(LocalDateTime.now());
                }
            } else {
                analisis.setFechaAnalisis(LocalDateTime.now());
            }
            
            System.out.println("Análisis creado: " + analisis);
            System.out.println("Intentando guardar en base de datos...");
            
            AnalisisResultado nuevoAnalisis = analisisService.crearAnalisis(analisis);
            System.out.println("✅ Análisis guardado exitosamente con ID: " + nuevoAnalisis.getId());
            return ResponseEntity.ok(convertToDTO(nuevoAnalisis));
            
        } catch (Exception e) {
            System.err.println("❌ Error creando análisis: " + e.getMessage());
            System.err.println("Clase de excepción: " + e.getClass().getSimpleName());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/analisisResultados/{id}/confirmar")
    public ResponseEntity<AnalisisResultadoDTO> confirmarDiagnostico(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        
        Boolean confirmado = (Boolean) request.get("confirmado");
        AnalisisResultado analisisActualizado = analisisService.confirmarDiagnostico(id, confirmado);
        return ResponseEntity.ok(convertToDTO(analisisActualizado));
    }

    @PatchMapping("/analisisResultados/{id}")
    public ResponseEntity<AnalisisResultadoDTO> updateAnalisisResultado(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        
        AnalisisResultado analisisActualizado = new AnalisisResultado();
        if (request.containsKey("confirmado")) {
            analisisActualizado.setConfirmado((Boolean) request.get("confirmado"));
        }
        if (request.containsKey("diagnostico")) {
            analisisActualizado.setDiagnostico((String) request.get("diagnostico"));
        }
        
        AnalisisResultado resultado = analisisService.actualizarAnalisis(id, analisisActualizado);
        return ResponseEntity.ok(convertToDTO(resultado));
    }

    @DeleteMapping("/analisisResultados/{id}")
    public ResponseEntity<Void> deleteAnalisisResultado(@PathVariable Long id) {
        boolean eliminado = analisisService.eliminarAnalisis(id);
        if (eliminado) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> getEstadisticas() {
        Map<String, Object> estadisticas = analisisService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }

    // Endpoints adicionales para funcionalidades específicas
    
    @GetMapping("/analisisResultados/cultivo/{cultivoId}")
    public ResponseEntity<List<AnalisisResultadoDTO>> getAnalisisPorCultivo(@PathVariable String cultivoId) {
        List<AnalisisResultado> resultados = analisisService.obtenerAnalisisPorCultivo(cultivoId);
        List<AnalisisResultadoDTO> resultadosDTO = resultados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultadosDTO);
    }

    @GetMapping("/analisisResultados/recientes")
    public ResponseEntity<List<AnalisisResultadoDTO>> getAnalisisRecientes() {
        List<AnalisisResultado> resultados = analisisService.obtenerAnalisisRecientes();
        List<AnalisisResultadoDTO> resultadosDTO = resultados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultadosDTO);
    }

    @GetMapping("/alertasVisuales/cultivo/{cultivoId}")
    public ResponseEntity<List<AlertaVisual>> getAlertasPorCultivo(@PathVariable String cultivoId) {
        List<AlertaVisual> alertas = alertaService.obtenerAlertasPorCultivo(cultivoId);
        return ResponseEntity.ok(alertas);
    }

    @PostMapping("/alertasVisuales/{id}/resolver")
    public ResponseEntity<AlertaVisual> resolverAlerta(@PathVariable Long id) {
        AlertaVisual alertaResuelta = alertaService.resolverAlerta(id);
        return ResponseEntity.ok(alertaResuelta);
    }

    // Endpoint de debug para ver qué datos llegan
    @PostMapping("/analisisResultados/debug")
    public ResponseEntity<Map<String, Object>> debugAnalisisResultado(@RequestBody Map<String, Object> request) {
        System.out.println("=== DEBUG ANALISIS ===");
        System.out.println("Datos recibidos: " + request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("received", request);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", "debug_ok");
        
        return ResponseEntity.ok(response);
    }

    // Métodos de conversión entre Entity y DTO
    private AnalisisResultadoDTO convertToDTO(AnalisisResultado analisis) {
        return new AnalisisResultadoDTO(
                analisis.getId(),
                analisis.getImagen(),
                analisis.getDiagnostico(),
                analisis.getAnomalia(),
                analisis.getConfianza(),
                analisis.getRecomendaciones(),
                analisis.getCultivoId(),
                analisis.getFechaAnalisis(),
                analisis.getConfirmado(),
                analisis.getImagenPath(),
                analisis.getFechaCreacion(),
                analisis.getFechaActualizacion()
        );
    }

    private AnalisisResultado convertToEntity(AnalisisResultadoDTO dto) {
        AnalisisResultado analisis = new AnalisisResultado();
        analisis.setImagen(dto.getImagen());
        analisis.setDiagnostico(dto.getDiagnostico());
        analisis.setAnomalia(dto.getAnomalia());
        analisis.setConfianza(dto.getConfianza());
        analisis.setRecomendaciones(dto.getRecomendaciones());
        analisis.setCultivoId(dto.getCultivoId());
        analisis.setImagenPath(dto.getImagenPath());
        if (dto.getFechaAnalisis() != null) {
            analisis.setFechaAnalisis(dto.getFechaAnalisis());
        }
        if (dto.getConfirmado() != null) {
            analisis.setConfirmado(dto.getConfirmado());
        }
        return analisis;
    }
}
