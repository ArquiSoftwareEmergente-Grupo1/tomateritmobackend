package com.tomateritmo.arqemergente.cultivos.interfaces.REST;

import com.tomateritmo.arqemergente.cultivos.domain.model.commands.DeleteCultivoCommand;
import com.tomateritmo.arqemergente.cultivos.domain.model.queries.GetAllCultivosQuery;
import com.tomateritmo.arqemergente.cultivos.domain.model.queries.GetCultivoByIdQuery;
import com.tomateritmo.arqemergente.cultivos.domain.model.queries.GetCultivosByUserIdQuery;
import com.tomateritmo.arqemergente.cultivos.domain.services.CultivoCommandService;
import com.tomateritmo.arqemergente.cultivos.domain.services.CultivoQueryService;
import com.tomateritmo.arqemergente.cultivos.interfaces.REST.resources.*;
import com.tomateritmo.arqemergente.cultivos.interfaces.REST.transform.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/cultivos", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Cultivos")
public class CultivosController {
    private final CultivoCommandService cultivoCommandService;
    private final CultivoQueryService cultivoQueryService;

    public CultivosController(CultivoCommandService cultivoCommandService, CultivoQueryService cultivoQueryService) {
        this.cultivoCommandService = cultivoCommandService;
        this.cultivoQueryService = cultivoQueryService;
    }

    @PostMapping
    public ResponseEntity<CultivoResource> createCultivo(@RequestBody CreateCultivoResource resource) {
        var command = CreateCultivoCommandFromResourceAssembler.toCommandFromResource(resource);
        var cultivo = cultivoCommandService.handle(command);
        return cultivo.map(c -> new ResponseEntity<>(CultivoResourceFromEntityAssembler.toResourceFromEntity(c), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{cultivoId}")
    public ResponseEntity<CultivoResource> getCultivoById(@PathVariable Long cultivoId) {
        var query = new GetCultivoByIdQuery(cultivoId);
        var cultivo = cultivoQueryService.handle(query);
        return cultivo.map(c -> ResponseEntity.ok(CultivoResourceFromEntityAssembler.toResourceFromEntity(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CultivoResource>> getAllCultivos(@RequestParam(required = false) Long userId) {
        List<CultivoResource> cultivoResources;
        
        if (userId != null) {
            var query = new GetCultivosByUserIdQuery(userId);
            var cultivos = cultivoQueryService.handle(query);
            cultivoResources = cultivos.stream().map(CultivoResourceFromEntityAssembler::toResourceFromEntity).toList();
        } else {
            var query = new GetAllCultivosQuery();
            var cultivos = cultivoQueryService.handle(query);
            cultivoResources = cultivos.stream().map(CultivoResourceFromEntityAssembler::toResourceFromEntity).toList();
        }
        
        return ResponseEntity.ok(cultivoResources);
    }

    @PutMapping("/{cultivoId}")
    public ResponseEntity<CultivoResource> updateCultivo(@PathVariable Long cultivoId, @RequestBody UpdateCultivoResource resource) {
        var command = UpdateCultivoCommandFromResourceAssembler.toCommandFromResource(resource);
        var cultivo = cultivoCommandService.handle(cultivoId, command);
        return cultivo.map(c -> ResponseEntity.ok(CultivoResourceFromEntityAssembler.toResourceFromEntity(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cultivoId}")
    public ResponseEntity<Void> deleteCultivo(@PathVariable Long cultivoId) {
        var command = new DeleteCultivoCommand(cultivoId);
        try {
            cultivoCommandService.handle(command);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found si el ID no existe
        }
    }
}
