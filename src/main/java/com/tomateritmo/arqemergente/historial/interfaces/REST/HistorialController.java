package com.tomateritmo.arqemergente.historial.interfaces.REST;

import com.tomateritmo.arqemergente.historial.domain.model.queries.GetAllEventosHistoricosQuery;
import com.tomateritmo.arqemergente.historial.domain.model.queries.GetEventoHistoricoByIdQuery;
import com.tomateritmo.arqemergente.historial.domain.services.HistorialCommandService;
import com.tomateritmo.arqemergente.historial.domain.services.HistorialQueryService;
import com.tomateritmo.arqemergente.historial.interfaces.REST.resources.CreateEventoHistoricoResource;
import com.tomateritmo.arqemergente.historial.interfaces.REST.resources.EventoHistoricoResource;
import com.tomateritmo.arqemergente.historial.interfaces.REST.resources.FiltroHistorialResource;
import com.tomateritmo.arqemergente.historial.interfaces.REST.transform.CreateEventoHistoricoCommandFromResourceAssembler;
import com.tomateritmo.arqemergente.historial.interfaces.REST.transform.EventoHistoricoResourceFromEntityAssembler;
import com.tomateritmo.arqemergente.historial.interfaces.REST.transform.GetEventosHistoricosByFilterQueryFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/eventosHistoricos", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Historial")
public class HistorialController {
    
    private final HistorialCommandService historialCommandService;
    private final HistorialQueryService historialQueryService;
    
    public HistorialController(HistorialCommandService historialCommandService, HistorialQueryService historialQueryService) {
        this.historialCommandService = historialCommandService;
        this.historialQueryService = historialQueryService;
    }
    
    @PostMapping
    public ResponseEntity<EventoHistoricoResource> createEvento(@RequestBody CreateEventoHistoricoResource resource) {
        var command = CreateEventoHistoricoCommandFromResourceAssembler.toCommandFromResource(resource);
        var evento = historialCommandService.handle(command);
        return evento.map(e -> new ResponseEntity<>(EventoHistoricoResourceFromEntityAssembler.toResourceFromEntity(e), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    
    @GetMapping
    public ResponseEntity<List<EventoHistoricoResource>> getEventosHistoricos(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String zona,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        
        List<EventoHistoricoResource> recursos;
        
        // Si hay filtros, usarlos
        if (tipo != null || zona != null || fechaInicio != null || fechaFin != null) {
            FiltroHistorialResource filtro = new FiltroHistorialResource(
                    tipo, 
                    zona, 
                    fechaInicio != null ? java.time.LocalDateTime.parse(fechaInicio) : null,
                    fechaFin != null ? java.time.LocalDateTime.parse(fechaFin) : null
            );
            
            var query = GetEventosHistoricosByFilterQueryFromResourceAssembler.toQueryFromResource(filtro);
            var eventos = historialQueryService.handle(query);
            recursos = eventos.stream()
                    .map(EventoHistoricoResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        } else {
            // Si no hay filtros, traer todos usando el método directo
            var eventos = historialQueryService.getAllEventosHistoricos();
            recursos = eventos.stream()
                    .map(EventoHistoricoResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(recursos);
    }
    
    /**
     * Endpoint adicional para obtener todos los eventos históricos sin filtros
     * Método utilitario que hace más explícita la acción de traer todos los eventos
     */
    @GetMapping("/all")
    public ResponseEntity<List<EventoHistoricoResource>> getAllEventosHistoricos() {
        var eventos = historialQueryService.getAllEventosHistoricos();
        var recursos = eventos.stream()
                .map(EventoHistoricoResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recursos);
    }
    
    @GetMapping("/{eventoId}")
    public ResponseEntity<EventoHistoricoResource> getEventoById(@PathVariable Long eventoId) {
        var query = new GetEventoHistoricoByIdQuery(eventoId);
        var evento = historialQueryService.handle(query);
        return evento.map(e -> ResponseEntity.ok(EventoHistoricoResourceFromEntityAssembler.toResourceFromEntity(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
