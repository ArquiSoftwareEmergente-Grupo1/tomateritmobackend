package com.tomateritmo.arqemergente.sensores.interfaces.REST;

import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetDashboardDataQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetLatestLecturasQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetSensorHistoryQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.valueobjects.TipoSensor;
import com.tomateritmo.arqemergente.sensores.domain.services.SensorCommandService;
import com.tomateritmo.arqemergente.sensores.domain.services.SensorQueryService;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.DashboardDataResource;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.LecturaSensorResource;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.RegistrarLecturaResource;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.SensorHistoryDataResource;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.transform.LecturaSensorResourceFromEntityAssembler;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.transform.RegistrarLecturaCommandFromResourceAssembler;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/sensores", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Sensores")
public class SensoresController {

    private final SensorCommandService sensorCommandService;
    private final SensorQueryService sensorQueryService;

    public SensoresController(SensorCommandService sensorCommandService, SensorQueryService sensorQueryService) {
        this.sensorCommandService = sensorCommandService;
        this.sensorQueryService = sensorQueryService;
    }

    @PostMapping("/lecturas")
    public ResponseEntity<?> registrarLectura(@RequestBody RegistrarLecturaResource resource) {
        var command = RegistrarLecturaCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = sensorCommandService.handle(command);
        return result.isPresent() ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                ResponseEntity.badRequest().build();
    }

    @GetMapping("/lecturas")
    public ResponseEntity<List<LecturaSensorResource>> getLatestLecturas(
            @RequestParam(defaultValue = "20") int limit) {

        var query = new GetLatestLecturasQuery(limit);
        var lecturas = sensorQueryService.handle(query);
        var resources = lecturas.stream()
                .map(LecturaSensorResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDataResource> getDashboardData() {
        var query = new GetDashboardDataQuery();
        return sensorQueryService.handle(query)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/historial")
    public ResponseEntity<SensorHistoryDataResource> getHistorial(
            @RequestParam List<TipoSensor> tipos,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin
    ) {
        var query = new GetSensorHistoryQuery(tipos, inicio, fin);
        return sensorQueryService.handle(query)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
