package eu.hlavki.vacation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Stream;

public class Vacation {

    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    public Vacation(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Vacation(Date dateFrom, Date dateTo) {
        this.dateFrom = dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.dateTo = dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public Stream<LocalDate> stream() {
        return Stream.iterate(dateFrom, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(dateFrom, dateTo) + 1);
    }
}
