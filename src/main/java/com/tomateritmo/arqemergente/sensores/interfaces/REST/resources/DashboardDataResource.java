package com.tomateritmo.arqemergente.sensores.interfaces.REST.resources;

public record DashboardDataResource(
        Double humedadSuelo,
        Double temperatura,
        Double ph,
        Double ec,
        Double luminosidad
) {}