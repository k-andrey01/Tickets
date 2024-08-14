package org.example;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {

    public static long calculateFlightDuration(LocalDate departureDate, LocalTime departureTime,
                                               LocalDate arrivalDate, LocalTime arrivalTime) {
        LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);
        LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);

        Duration duration = Duration.between(departureDateTime, arrivalDateTime);
        return duration.toMinutes();
    }

    public static LocalTime parseTime(String timeString) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("H:mm");

        try {
            return LocalTime.parse(timeString, formatter1);
        } catch(DateTimeParseException ex) {
            return LocalTime.parse(timeString, formatter2);
        }
    }
}
