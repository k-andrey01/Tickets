package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        TicketService ticketService = new TicketService();

        List<Ticket> tickets = ticketService.getFlightsFromJson("tickets.json");
        List<Ticket> filteredTickets = tickets.stream()
                .filter(ticket -> ticket.getOriginName().equals("Владивосток") && ticket.getDestinationName().equals("Тель-Авив"))
                .collect(Collectors.toList());

        System.out.println("Разница между средней ценой и медианой для полета между городами Владивосток и Тель-Авив: "
                + ticketService.getDiffPrice(filteredTickets));
        System.out.println("=====================================================================");

        System.out.println("Минимальное время полета для каждого авиаперевозчика:");
        for (Map.Entry<String, Long> entry : ticketService.getMinTime(filteredTickets).entrySet()) {
            System.out.println("Перевозчик: " + entry.getKey() + ", Время полета: " + entry.getValue() + " минут");
        }
    }
}