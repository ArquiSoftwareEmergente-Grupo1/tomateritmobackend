package com.tomateritmo.arqemergente.sensores.application.internal.queryservices;

import com.tomateritmo.arqemergente.sensores.domain.model.entities.LecturaSensor;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetDashboardDataQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetSensorHistoryQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.valueobjects.TipoSensor;
import com.tomateritmo.arqemergente.sensores.domain.services.SensorQueryService;
import com.tomateritmo.arqemergente.sensores.infrastructure.persistence.jpa.repositories.LecturaSensorRepository;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.DashboardDataResource;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.SensorHistoryDataResource;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SensorQueryServiceImpl implements SensorQueryService {

    private final LecturaSensorRepository lecturaSensorRepository;

    public SensorQueryServiceImpl(LecturaSensorRepository lecturaSensorRepository) {
        this.lecturaSensorRepository = lecturaSensorRepository;
    }

    @Override
    public Optional<DashboardDataResource> handle(GetDashboardDataQuery query) {
        var humedad = lecturaSensorRepository
                .findTopByTipoSensorOrderByTimestampDesc(TipoSensor.HUMEDAD_SUELO);
        var temperatura = lecturaSensorRepository
                .findTopByTipoSensorOrderByTimestampDesc(TipoSensor.TEMPERATURA);
        var ph = lecturaSensorRepository
                .findTopByTipoSensorOrderByTimestampDesc(TipoSensor.PH);
        var ec = lecturaSensorRepository
                .findTopByTipoSensorOrderByTimestampDesc(TipoSensor.EC);
        var luminosidad = lecturaSensorRepository
                .findTopByTipoSensorOrderByTimestampDesc(TipoSensor.LUMINOSIDAD);

        var data = new DashboardDataResource(
                humedad.map(LecturaSensor::getValor).orElse(null),
                temperatura.map(LecturaSensor::getValor).orElse(null),
                ph.map(LecturaSensor::getValor).orElse(null),
                ec.map(LecturaSensor::getValor).orElse(null),
                luminosidad.map(LecturaSensor::getValor).orElse(null)
        );

        return Optional.of(data);
    }

    @Override
    public Optional<SensorHistoryDataResource> handle(GetSensorHistoryQuery query) {
        List<LecturaSensor> lecturas = lecturaSensorRepository
                .findByTipoSensorInAndTimestampBetween(query.tiposSensor(), query.fechaInicio(), query.fechaFin());

        // Agrupamos las lecturas por tipo de sensor
        Map<TipoSensor, List<LecturaSensor>> historialAgrupado = lecturas.stream()
                .collect(Collectors.groupingBy(LecturaSensor::getTipoSensor));

        Map<String, List<SensorHistoryDataResource.DataPoint>> series = new HashMap<>();

        historialAgrupado.forEach((tipo, listaLecturas) -> {
            List<SensorHistoryDataResource.DataPoint> puntos = listaLecturas.stream()
                    .map(l -> new SensorHistoryDataResource.DataPoint(l.getTimestamp(), l.getValor()))
                    .collect(Collectors.toList());
            series.put(tipo.name(), puntos);
        });

        return Optional.of(new SensorHistoryDataResource(series));
    }
}
