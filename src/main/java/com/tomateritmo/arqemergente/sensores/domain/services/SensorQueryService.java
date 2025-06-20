package com.tomateritmo.arqemergente.sensores.domain.services;

import com.tomateritmo.arqemergente.sensores.domain.model.entities.LecturaSensor;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetDashboardDataQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetLatestLecturasQuery;
import com.tomateritmo.arqemergente.sensores.domain.model.queries.GetSensorHistoryQuery;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.DashboardDataResource;
import com.tomateritmo.arqemergente.sensores.interfaces.REST.resources.SensorHistoryDataResource;
import java.util.List;
import java.util.Optional;

public interface    SensorQueryService {
    Optional<DashboardDataResource> handle(GetDashboardDataQuery query);
    Optional<SensorHistoryDataResource> handle(GetSensorHistoryQuery query);
    List<LecturaSensor> handle(GetLatestLecturasQuery query);
}
