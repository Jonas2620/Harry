import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.TreeMap;

public class Booking {
    private final Calendar calendar;
    private final Scanner scanner;

    // Konstruktør: Initialiser Booking med en kalender og en scanner
    public Booking(Calendar calendar) {
        this.calendar = calendar;
        this.scanner = new Scanner(System.in);
    }

    // Opret en booking for en given dato og tidspunkt
    public void makeBooking(LocalDate date, String chosenTime) {
        // Tjek om booking er tilladt for den pågældende dato og tidspunkt
        if (isBookingAllowed(date, chosenTime)) {
            // Bekræft bookingen
            confirmBooking(date, chosenTime);
        }
    }

    // Opret en booking baseret på brugerens input
    public void makeBookingWithUserInput(LocalDate date) {
        System.out.print("Indtast ønsket tidspunkt (hh:mm - hh:mm): ");
        String chosenTime = scanner.nextLine();

        // Opret booking ved hjælp af brugerens input
        makeBooking(date, chosenTime);
    }

    // Tjek om booking er tilladt på en given dato og tidspunkt
    private boolean isBookingAllowed(LocalDate date, String chosenTime) {
        // Tjek om det er en feriedag eller weekenddag, og om tidspunktet er ledigt
        if (calendar.isHoliday(date) || isWeekend(date)) {
            System.out.println("Dagen er lukket for booking.");
            return false;
        }

        TreeMap<String, String> timeSlots = calendar.getDailySlots(date);
        if (timeSlots == null) {
            System.out.println("Ingen ledige tider på denne dato.");
            return false;
        }

        if (timeSlots.get(chosenTime) != null && timeSlots.get(chosenTime).equals("Available")) {
            // Booking er tilladt, tidspunktet er ledigt
            return true;
        } else {
            System.out.println("Den valgte tid er ikke ledig.");
            return false;
        }
    }

    // Bekræft booking og opdater kalenderen
    private void confirmBooking(LocalDate date, String chosenTime) {
        TreeMap<String, String> timeSlots = calendar.getDailySlots(date);
        if (timeSlots != null) {
            // Opdater tidspunktet til "Booked"
            timeSlots.put(chosenTime, "Booked");
            System.out.println("Booking bekræftet for " + date + " kl. " + chosenTime);
        }
    }

    // Tjek om en given dato er en weekenddag
    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
