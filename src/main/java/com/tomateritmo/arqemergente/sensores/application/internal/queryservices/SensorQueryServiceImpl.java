package com.tomateritmo.arqemergente.sensores.application.internal.queryservices;

import com.tomateritmo.arqemergente.sensores.domain.model.entities.LecturaSensor;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetDashboardDataQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetLatestLecturasQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetRecomendacionesCultivosQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetSensorHistoryQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.valueobjects.TipoSensor;
import com.tomateritmo.arqemergente.sensores.domain.services.SensorQueryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.tomateritmo.arqemergente.sensores.infrastructure.persistence.jpa.repositories.LecturaSensorRepository;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.DashboardDataResource;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.RecomendacionesCultivosResource;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.SensorHistoryDataResource;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<LecturaSensor> handle(GetLatestLecturasQuery query) {
        // Creamos un objeto Pageable para limitar los resultados
        Pageable limit = PageRequest.of(0, query.limit()); // Página 0, con el tamaño del límite
        return lecturaSensorRepository.findByOrderByTimestampDesc(limit);
    }
    
    @Override
    public Optional<RecomendacionesCultivosResource> handle(GetRecomendacionesCultivosQuery query) {
        // Obtenemos primero los datos actuales del dashboard
        var dashboardData = handle(new GetDashboardDataQuery());
        
        if (dashboardData.isEmpty() || 
            dashboardData.get().humedadSuelo() == null || 
            dashboardData.get().temperatura() == null || 
            dashboardData.get().luminosidad() == null) {
            return Optional.empty();
        }
        
        DashboardDataResource data = dashboardData.get();
        
        // Valores ideales para cultivo de tomate
        final double HUMEDAD_IDEAL_MIN = 60.0;
        final double HUMEDAD_IDEAL_MAX = 80.0;
        final double TEMPERATURA_IDEAL_MIN = 20.0;
        final double TEMPERATURA_IDEAL_MAX = 26.0;
        final double LUMINOSIDAD_IDEAL_MIN = 20000.0;
        final double LUMINOSIDAD_IDEAL_MAX = 80000.0;
        
        // Variables para las recomendaciones
        boolean activarRiego = false;
        boolean abrirCompuertasLuz = false;
        boolean activarVentilacion = false;
        List<String> recomendaciones = new ArrayList<>();
        
        // Evaluación de humedad del suelo
        String nivelHumedad;
        if (data.humedadSuelo() < HUMEDAD_IDEAL_MIN) {
            activarRiego = true;
            nivelHumedad = "BAJO";
            recomendaciones.add("Activar sistema de riego: La humedad del suelo está baja (" + 
                               data.humedadSuelo() + "%). Valor ideal: " + HUMEDAD_IDEAL_MIN + "% - " + HUMEDAD_IDEAL_MAX + "%.");
        } else if (data.humedadSuelo() > HUMEDAD_IDEAL_MAX) {
            nivelHumedad = "ALTO";
            recomendaciones.add("Suspender riego: La humedad del suelo está alta (" + 
                               data.humedadSuelo() + "%). Valor ideal: " + HUMEDAD_IDEAL_MIN + "% - " + HUMEDAD_IDEAL_MAX + "%.");
        } else {
            nivelHumedad = "NORMAL";
            recomendaciones.add("Humedad de suelo óptima (" + data.humedadSuelo() + "%).");
        }
        
        // Evaluación de temperatura
        String nivelTemperatura;
        if (data.temperatura() < TEMPERATURA_IDEAL_MIN) {
            nivelTemperatura = "BAJO";
            recomendaciones.add("Aumentar temperatura: La temperatura está baja (" + 
                               data.temperatura() + "°C). Valor ideal: " + TEMPERATURA_IDEAL_MIN + "°C - " + TEMPERATURA_IDEAL_MAX + "°C.");
        } else if (data.temperatura() > TEMPERATURA_IDEAL_MAX) {
            activarVentilacion = true;
            nivelTemperatura = "ALTO";
            recomendaciones.add("Activar ventilación: La temperatura está alta (" + 
                               data.temperatura() + "°C). Valor ideal: " + TEMPERATURA_IDEAL_MIN + "°C - " + TEMPERATURA_IDEAL_MAX + "°C.");
        } else {
            nivelTemperatura = "NORMAL";
            recomendaciones.add("Temperatura óptima (" + data.temperatura() + "°C).");
        }
        
        // Evaluación de luminosidad
        String nivelLuminosidad;
        if (data.luminosidad() < LUMINOSIDAD_IDEAL_MIN) {
            abrirCompuertasLuz = true;
            nivelLuminosidad = "BAJO";
            recomendaciones.add("Abrir compuertas de luz: La luminosidad está baja (" + 
                               data.luminosidad() + " lux). Valor ideal: " + LUMINOSIDAD_IDEAL_MIN + " - " + LUMINOSIDAD_IDEAL_MAX + " lux.");
        } else if (data.luminosidad() > LUMINOSIDAD_IDEAL_MAX) {
            nivelLuminosidad = "ALTO";
            recomendaciones.add("Cerrar compuertas de luz: La luminosidad está alta (" + 
                               data.luminosidad() + " lux). Valor ideal: " + LUMINOSIDAD_IDEAL_MIN + " - " + LUMINOSIDAD_IDEAL_MAX + " lux.");
        } else {
            nivelLuminosidad = "NORMAL";
            recomendaciones.add("Luminosidad óptima (" + data.luminosidad() + " lux).");
        }
        
        // pH y conductividad eléctrica
        if (data.ph() != null) {
            if (data.ph() < 5.5) {
                recomendaciones.add("El pH está bajo (" + data.ph() + "). Considerar ajustar la nutrición para aumentarlo.");
            } else if (data.ph() > 6.5) {
                recomendaciones.add("El pH está alto (" + data.ph() + "). Considerar ajustar la nutrición para reducirlo.");
            } else {
                recomendaciones.add("pH óptimo (" + data.ph() + ").");
            }
        }
        
        if (data.ec() != null) {
            if (data.ec() < 2.0) {
                recomendaciones.add("La conductividad eléctrica está baja (" + data.ec() + "). Considerar aumentar la concentración de nutrientes.");
            } else if (data.ec() > 3.5) {
                recomendaciones.add("La conductividad eléctrica está alta (" + data.ec() + "). Considerar diluir la solución nutritiva.");
            } else {
                recomendaciones.add("Conductividad eléctrica óptima (" + data.ec() + ").");
            }
        }
        
        return Optional.of(new RecomendacionesCultivosResource(
            activarRiego,
            abrirCompuertasLuz,
            activarVentilacion,
            recomendaciones,
            nivelHumedad,
            nivelLuminosidad,
            nivelTemperatura
        ));
    }
}
