package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TicketService {

    public List<Ticket> getFlightsFromJson(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
            Gson gson = new Gson();

            String json = "";
            String line = reader.readLine();
            while (line != null){
                json += line;
                line = reader.readLine();
            }
            Map<String, List<Ticket>> tickets = gson.fromJson(json, new TypeToken<Map<String, List<Ticket>>>() {
            }.getType());

            return tickets.get("tickets");
        }
    }

    public Map<String, Long> getMinTime(List<Ticket> tickets){
        Map<String, Long> carrierMinFlightTimes = new HashMap<>();

        for (Ticket ticket : tickets) {
            long flightDuration = Utils.calculateFlightDuration(
                    LocalDate.parse(ticket.getDepartureDate(), DateTimeFormatter.ofPattern("dd.MM.yy")),
                    Utils.parseTime(ticket.getDepartureTime()),
                    LocalDate.parse(ticket.getArrivalDate(), DateTimeFormatter.ofPattern("dd.MM.yy")),
                    Utils.parseTime(ticket.getArrivalTime()));
            carrierMinFlightTimes.put(ticket.getCarrier(), Math.min(carrierMinFlightTimes.getOrDefault(ticket.getCarrier(), Long.MAX_VALUE), flightDuration));
        }

        return carrierMinFlightTimes;
    }

    public double getDiffPrice(List<Ticket> tickets){
        Double avgPrice;
        Double medianPrice;

        List<Ticket> sortedTicketsByCities = tickets.stream()
                .sorted(Comparator.comparing(Ticket::getPrice)).collect(Collectors.toList());

        avgPrice = sortedTicketsByCities.stream().mapToDouble(Ticket::getPrice).average().getAsDouble();
        int size = sortedTicketsByCities.size();
        if (size % 2 == 0) {
            medianPrice = (tickets.get(size/2-1).getPrice() + tickets.get(size/2).getPrice())/ 2.0;
        } else {
            medianPrice = tickets.get(size/2).getPrice();
        }

        return Math.abs(avgPrice-medianPrice);
    }
}
