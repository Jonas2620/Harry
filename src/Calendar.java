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

    //Metoder der laver kalender
    private void createCalendar() {
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            TreeMap<String, String> dailySlots = new TreeMap<>();
            String status = isWeekend(date) ? "Lukket (Weekend)" : "Ledig";
            createDailySlots(dailySlots, status);
            calendar.put(date, dailySlots);
        }
    }

    //Metode der tjekker om dagen er en weekenddag
    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    //Metode der laver 8 timeslots på en dag (fra kl.10:00-18:00)
    private void createDailySlots(TreeMap<String, String> dailySlots, String status) {
        for (int hour = 10; hour < 18; hour++) {
            String timeSlot = String.format("%d:00 - %d:00", hour, hour + 1);
            dailySlots.put(timeSlot, status);
        }
    }

    // Vis en valgt dato
    public void showDate(String inputShowDate) {
        LocalDate date = LocalDate.parse(inputShowDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // metode der viser timeslots og tilgængelighed.
        TreeMap<String, String> dailySlots = getDailySlots(date);

        if (dailySlots == null) {
            System.out.println(date.format(formatter) + ": Ugyldigt valg");
        } else {
            System.out.println(date.format(formatter) + ":");
            dailySlots.forEach((timeSlot, availability) -> {
                System.out.println(timeSlot + " - " + availability);
            });
        }
        System.out.println();
    }

    // Metode der henter timeslots
    public TreeMap<String, String> getDailySlots(LocalDate date) {
        return calendar.get(date);
    }

    public void registerHoliday(String inputRegisterHoliday) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(inputRegisterHoliday, formatter);

        TreeMap<String, String> dailySlots = calendar.get(date);
        if (dailySlots != null) {
            for (String timeSlot : dailySlots.keySet()) {
                dailySlots.put(timeSlot, "Ferie/Fri");
            }
            System.out.println("Ferie/Fri er registreret for " + inputRegisterHoliday);
        } else {
            System.out.println("Datoen " + inputRegisterHoliday + " findes ikke i kalenderen.");
        }
        System.out.println();
    }

}


