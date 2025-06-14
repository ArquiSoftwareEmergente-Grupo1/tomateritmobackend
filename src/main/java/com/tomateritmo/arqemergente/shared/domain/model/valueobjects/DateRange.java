package com.iot.error404.chakiy.shared.domain.model.valueobjects;

import lombok.Getter;
import java.time.LocalDate;
import java.time.Period;


public class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public DateRange() {
    }

    public DateRange(LocalDate startDate, int monthsToAdd) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        this.startDate = startDate;
        this.endDate = startDate.plus(Period.ofMonths(monthsToAdd));
    }
}