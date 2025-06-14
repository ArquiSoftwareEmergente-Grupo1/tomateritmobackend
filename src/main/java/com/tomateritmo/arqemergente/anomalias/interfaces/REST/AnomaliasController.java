package com.tomateritmo.arqemergente.anomalias.interfaces.REST;

import com.tomateritmo.arqemergente.anomalias.domain.model.queries.GetAllAnomaliasByCultivoIdQuery;
import com.tomateritmo.arqemergente.anomalias.domain.model.queries.GetAllAnomaliasQuery;
import com.tomateritmo.arqemergente.anomalias.domain.model.queries.GetAnomaliaByIdQuery;
import com.tomateritmo.arqemergente.anomalias.domain.services.AnomaliaCommandService;
import com.tomateritmo.arqemergente.anomalias.domain.services.AnomaliaQueryService;
import com.tomateritmo.arqemergente.anomalias.interfaces.REST.resources.AnomaliaResource;
import com.tomateritmo.arqemergente.anomalias.interfaces.REST.resources.CreateAnomaliaResource;
import com.tomateritmo.arqemergente.anomalias.interfaces.REST.transform.AnomaliaResourceFromEntityAssembler;
import com.tomateritmo.arqemergente.anomalias.interfaces.REST.transform.CreateAnomaliaCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/anomalias", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Anomalías")
public class AnomaliasController {

    private final AnomaliaCommandService anomaliaCommandService;
    private final AnomaliaQueryService anomaliaQueryService;

    public AnomaliasController(AnomaliaCommandService anomaliaCommandService, AnomaliaQueryService anomaliaQueryService) {
        this.anomaliaCommandService = anomaliaCommandService;
        this.anomaliaQueryService = anomaliaQueryService;
    }

    @PostMapping
    public ResponseEntity<AnomaliaResource> createAnomalia(@RequestBody CreateAnomaliaResource resource) {
        var command = CreateAnomaliaCommandFromResourceAssembler.toCommandFromResource(resource);
        var anomalia = anomaliaCommandService.handle(command);
        return anomalia.map(a ->
                        new ResponseEntity<>(AnomaliaResourceFromEntityAssembler.toResourceFromEntity(a), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{anomaliaId}")
    public ResponseEntity<AnomaliaResource> getAnomaliaById(@PathVariable Long anomaliaId) {
        var query = new GetAnomaliaByIdQuery(anomaliaId);
        var anomalia = anomaliaQueryService.handle(query);
        return anomalia.map(a -> ResponseEntity.ok(AnomaliaResourceFromEntityAssembler.toResourceFromEntity(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AnomaliaResource>> getAllAnomalias() {
        var query = new GetAllAnomaliasQuery();
        var anomalias = anomaliaQueryService.handle(query);
        var anomaliaResources = anomalias.stream()
                .map(AnomaliaResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(anomaliaResources);
    }

    @GetMapping("/cultivo/{cultivoId}")
    public ResponseEntity<List<AnomaliaResource>> getAnomaliasByCultivoId(@PathVariable Long cultivoId) {
        var query = new GetAllAnomaliasByCultivoIdQuery(cultivoId);
        var anomalias = anomaliaQueryService.handle(query);
        var anomaliaResources = anomalias.stream()
                .map(AnomaliaResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(anomaliaResources);
    }
}
