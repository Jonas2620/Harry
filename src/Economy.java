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

        System.out.println("Bookede tider for d. " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ":");
        for (BookingSlots booking : calendar.calendar) {
            if (booking.date.equals(date) && !"Ledig".equals(booking.status)) {
                System.out.println(counter + ". " + booking.timeSlot + " - " + booking.status);
                counter++;
                found = true;
            }
        }

        if (!found) {
            System.out.println("Ingen bookinger fundet for d. " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            return;
        }

        System.out.print("Vælg en tid for at markere som 'Kredit' (nummer): ");
        try {
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
        } catch (NumberFormatException e){
            System.out.println("Ugyldigt valg. Prøv igen.");
        }
    }

    public void deleteCredit() {
        int counter = 1;
        boolean found = false;

        System.out.println("Bookinger markeret som 'Kredit':");
        ArrayList<BookingSlots> creditBookings = new ArrayList<>();

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
        try {
            int selectedTime = Integer.parseInt(scanner.nextLine());

            if (selectedTime > 0 && selectedTime <= creditBookings.size()) {
                BookingSlots selectedBooking = creditBookings.get(selectedTime - 1);
                selectedBooking.status = selectedBooking.status.replace(" (Kredit)", "");
                System.out.println("Tiden " + selectedBooking.timeSlot + " den " + selectedBooking.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " er nu fjernet fra 'Kredit'.");
            } else {
                System.out.println("Ugyldigt valg. Prøv igen.");
            }
        } catch (NumberFormatException e){
            System.out.println("Ugyldigt valg. Prøv igen.");

        }
    }

    public void addPurchasedProducts() {
        LocalDate date = InputHelper.inputHelperShowDate();
        ArrayList<BookingSlots> bookedSlots = calendar.getDailySlots(date);
        ArrayList<BookingSlots> nonEmptySlots = new ArrayList<>();
        int counter = 1;

        for (BookingSlots slot : bookedSlots) {
            if (!"Ledig".equals(slot.status)) {
                nonEmptySlots.add(slot);
                System.out.println(counter + ". " + slot.timeSlot + " - " + slot.status);
                counter++;
            }
        }

        if (nonEmptySlots.isEmpty()) {
            System.out.println("Ingen bookinger fundet for d. " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            return;
        }

        System.out.print("Vælg en tid (nummer) for at tilføje tilkøbte produkter: ");
        try {
            int selectedTime = Integer.parseInt(scanner.nextLine());

            if (selectedTime > 0 && selectedTime <= nonEmptySlots.size()) {
                BookingSlots selectedSlot = nonEmptySlots.get(selectedTime - 1);
                System.out.print("Indtast beløb for tilkøbte produkter: ");
                int amount = Integer.parseInt(scanner.nextLine());
                amount += 200; // Plus 200 kr for standard klipning
                selectedSlot.amount = amount;  // Gem beløbet i BookingSlots
                System.out.println("Beløbet for tilkøbte produkter og klipning er nu: " + amount + " kr.");
            } else {
                System.out.println("Ugyldigt valg. Prøv igen.");
            }
        } catch (NumberFormatException e){
            System.out.println("Ugyldigt valg. Prøv igen.");
        }
    }


    public void dailyEconomicReport() {
        int paidAmount = 0;
        int creditAmount = 0;

        LocalDate date = InputHelper.inputHelperShowDate();

        if (date.isAfter(LocalDate.now().minusDays(1))) {
            System.out.println("Du kan kun søge op til dagen før dags dato.");
            return;
        }

        ArrayList<BookingSlots> bookedSlots = calendar.getDailySlots(date);

        if (bookedSlots.isEmpty()) {
            System.out.println("Ingen bookinger fundet for d. " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            return;
        }

        for (BookingSlots booking : calendar.calendar) {
            if (booking.date.equals(date)) {
                if (!"Ledig".equals(booking.status)) {
                    System.out.print("Booking tid: " + booking.timeSlot + ", Status: " + booking.status);

                    if (booking.status.endsWith("(Kredit)")) {
                        creditAmount += booking.amount;  //
                        System.out.println(", Pris: " + booking.amount + "kr.");
                    } else {
                        paidAmount += booking.amount;
                        System.out.println(", Pris: " + booking.amount + " kr.");
                    }
                }
            }
        }
        int totalAmount = creditAmount + paidAmount;
        System.out.println("Afregnet beløb (betalt): " + paidAmount + " kr.");
        System.out.println("Udestående beløb (kredit): " + creditAmount + " kr.");
        System.out.println("Samlet beløb: " + totalAmount + " kr.");
    }
}
