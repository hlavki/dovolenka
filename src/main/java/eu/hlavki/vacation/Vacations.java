package eu.hlavki.vacation;

import biweekly.Biweekly;
import biweekly.ICalendar;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

public class Vacations {

    private final List<Vacation> vacations;
    private final Set<LocalDate> holidays;

    public Vacations(List<Vacation> vacations, Set<LocalDate> holidays) {
        this.vacations = vacations;
        this.holidays = holidays;
    }

    Stream<LocalDate> stream() {
        return vacations.stream()
                .flatMap(v -> v.stream())
                .filter(d -> !holidays.contains(d))
                .filter(d -> (d.getDayOfWeek() != DayOfWeek.SATURDAY) && (d.getDayOfWeek() != DayOfWeek.SUNDAY));
    }

    public static Vacations parse(String icalFile, String searchString) {
        return parse(icalFile, searchString, "/sviatky.txt");
    }

    public static Vacations parse(String icalFile, String searchString, String holidaysFile) {
        List<Vacation> vacations = new ArrayList<>();
        try (InputStream in = new FileInputStream(icalFile)) {
            ICalendar ical = Biweekly.parse(in).first();
            vacations = ical.getEvents().stream()
                    .filter(e -> e.getSummary().getValue().toLowerCase().contains(searchString.toLowerCase()))
                    .map(e -> new Vacation(e.getDateStart().getValue(), e.getDateEnd().getValue()))
                    .collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<LocalDate> holidays = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Vacations.class.getResourceAsStream(holidaysFile)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LocalDate date = LocalDate.parse(line.trim());
                holidays.add(date);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Vacations(vacations, holidays);
    }
}
