import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Economy {
    private final Calendar calendar;
    private final Scanner scanner;

    public Economy(Calendar calendar) {
        this.calendar = calendar;
        this.scanner = new Scanner(System.in);
    }

    public void markAsCredit() {
       LocalDate date = InputHelper.inputHelperShowDate();
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

    public void deleteCredit() {
        int counter = 1;
        boolean found = false;

        System.out.println("Bookinger markeret som 'Kredit':");
        ArrayList<BookingSlots> creditBookings = new ArrayList<>();

        calendar.calendar.sort((o1, o2) -> o1.date.compareTo(o2.date));

        for (BookingSlots booking : calendar.calendar) {
            if (booking.status.endsWith("(Kredit)")) {
                System.out.println(counter + ". " + booking.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " - " + booking.timeSlot + " - " + booking.status);
                creditBookings.add(booking);
                counter++;
                found = true;
            }
        }

        if (!found) {
            System.out.println("Ingen bookinger markeret som 'Kredit' fundet.");
            return;
        }

        System.out.print("Vælg en tid for at fjerne 'Kredit' markeringen (nummer): ");
        int selectedTime = Integer.parseInt(scanner.nextLine());

        if (selectedTime > 0 && selectedTime <= creditBookings.size()) {
            BookingSlots selectedBooking = creditBookings.get(selectedTime - 1);
            selectedBooking.status = selectedBooking.status.replace(" (Kredit)", "");
            System.out.println("Tiden " + selectedBooking.timeSlot + " den " + selectedBooking.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " er nu fjernet fra 'Kredit'.");
        } else {
            System.out.println("Ugyldigt valg. Prøv igen.");
        }
    }
}