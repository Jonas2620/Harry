import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

public class Calendar {
    private TreeMap<LocalDate, TreeMap<String, String>> calendar;

    public Calendar() {
        calendar = new TreeMap<>();
        createCalendar();
    }

    //Metode der laver kalender fra 1.10.2023-31.12-2024
    private void createCalendar() {
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            TreeMap<String, String> dailySlots = new TreeMap<>();
            String status = isWeekend(date) ? "Lukket (Weekend)" : "Available";
            createDailySlots(dailySlots, status);
            calendar.put(date, dailySlots);
        }
    }

    //Metode der tjekker om dagen er en weekenddag
    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    //Metode der laver 8 timeslots på en dag, startende fra kl.10:00-18:00.
    private void createDailySlots(TreeMap<String, String> dailySlots, String status) {
        for (int hour = 10; hour < 18; hour++) {
            String timeSlot = String.format("%d:00 - %d:00", hour, hour + 1);
            dailySlots.put(timeSlot, status);
        }
    }

    // Vis Timeslots
    public TreeMap<String, String> getDailySlots(LocalDate date) {
        return calendar.get(date);
    }

    // Vis en valgt dato
    public void showDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Vis datoen og dens tilgængelighed i kalenderen
        TreeMap<String, String> dailySlots = getDailySlots(date);

        if (dailySlots == null) {
            System.out.println(date.format(formatter) + ": Lukket");
        } else {
            System.out.println(date.format(formatter) + ":");
            dailySlots.forEach((timeSlot, availability) -> {
                System.out.println(timeSlot + " - " + availability);
            });

        }
        System.out.println();
    }
}


