import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

public class Calendar {
    private final TreeMap<LocalDate, TreeMap<String, String>> calendar;

    // Konstruktør: Initialiserer kalenderen og udfylder den med data
    public Calendar() {
        calendar = new TreeMap<>();
        populateCalendar();
    }

    // Udfylder kalenderen med data
    private void populateCalendar() {
        // Definér start- og slutdatoen for kalenderen
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Markér weekenddage som "Lukket (Weekend)"
            if (date.getDayOfWeek().equals(java.time.DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(java.time.DayOfWeek.SUNDAY)) {
                TreeMap<String, String> weekendSlots = new TreeMap<>();
                for (int hour = 10; hour < 18; hour++) {
                    String timeSlot = hour + ":00 - " + (hour + 1) + ":00";
                    weekendSlots.put(timeSlot, "Lukket (Weekend)");
                }
                calendar.put(date, weekendSlots);
            } else {
                // Arbejdsdage
                TreeMap<String, String> dailySlots = new TreeMap<>();
                for (int hour = 10; hour < 18; hour++) {
                    String timeSlot = hour + ":00 - " + (hour + 1) + ":00";
                    dailySlots.put(timeSlot, "Available");
                }
                calendar.put(date, dailySlots);
            }
        }
    }

    // Hent de daglige tidspunkter for en given dato
    public TreeMap<String, String> getDailySlots(LocalDate date) {
        return calendar.get(date);
    }

    // Tjek om en given dato er en feriedag eller weekenddag
    public boolean isHoliday(LocalDate date) {
        TreeMap<String, String> dailySlots = calendar.get(date);
        return dailySlots == null;
    }

    // Slet en booking på en bestemt dato og tidspunkt
    public void deleteBooking(LocalDate date, String timeSlot) {
        TreeMap<String, String> dailySlots = calendar.get(date);
        if (dailySlots != null) {
            dailySlots.put(timeSlot, "Available");
        }
    }

    // Registrér en ferie- eller fridag på en bestemt dato
    public void registerHoliday(LocalDate date) {
        if (!calendar.containsKey(date)) {
            System.out.println("Datoen er ikke i kalenderen.");
            return;
        }

        // Ændre tilgængeligheden for alle tidspunkter på den dag til "Ferie- eller Fridag"
        TreeMap<String, String> dailySlots = calendar.get(date);
        dailySlots.replaceAll((timeSlot, availability) -> "Ferie- eller Fridag");

        System.out.println("Ferie- eller fridagen på " + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " er blevet registreret.");
        System.out.println();
    }
}
