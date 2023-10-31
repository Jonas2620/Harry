import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Economy {
    private final Calendar calendar;
    private final Scanner scanner;

    public Economy(Calendar calendar) {
        this.calendar = calendar;
        this.scanner = new Scanner(System.in);
    }

    public void markAsCredit() {
       LocalDate date = InputHelper.inputHelperDate();

        int counter = 1;
        boolean found = false;

        System.out.println("Bookede tider for " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +":");
        for (BookingSlots booking : calendar.calendar) {
            if (booking.date.equals(date) && !"Ledig".equals(booking.status)) {
                System.out.println(counter + ". " + booking.timeSlot + " - " + booking.status);
                counter++;
                found = true;
            }
        }

        if (!found) {
            System.out.println("Ingen bookinger fundet for " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +":");
            return;
        }

        System.out.print("Vælg en tid for at markere som 'Kredit' (nummer): ");
        int selectedTime = Integer.parseInt(scanner.nextLine());

        counter = 1;
        for (BookingSlots booking : calendar.calendar) {
            if (booking.date.equals(date) && !"Ledig".equals(booking.status)) {
                if (counter == selectedTime) {
                    booking.status = booking.status + " (Kredit)";
                    System.out.println("Tiden " + booking.timeSlot + " er nu markeret som 'Kredit'.");
                    return;
                }
                counter++;
            }
        }

        System.out.println("Ugyldigt valg. Prøv igen.");
    }
}