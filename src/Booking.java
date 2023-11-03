import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Booking {
    private final Calendar calendar;
    private final Scanner scanner;

    public Booking(Calendar calendar) {
        this.calendar = calendar;
        this.scanner = new Scanner(System.in);
    }

    //Metode der opretter booking
    public void createBooking() {
        LocalDate date = InputHelper.inputHelperShowDate();
        ArrayList<BookingSlots> allAvailableSlots = new ArrayList<>();
        int counter = 1;

        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = date.plusDays(i);
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                ArrayList<BookingSlots> dailySlots = calendar.getDailySlots(currentDate);
                if (dailySlots.isEmpty()) {
                    System.out.println("Ingen ledige tider på denne dato.");
                } else {
                    allAvailableSlots.addAll(dailySlots);
                    System.out.println("Dato " + currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    for (BookingSlots slot : dailySlots) {
                        System.out.println(counter + ". " + slot.timeSlot + " - " + slot.status);
                        counter++;
                    }
                    System.out.println();
                }
            }
        }

        System.out.print("Vælg en tid (nummer): ");
        try {
            int selectedTime = Integer.parseInt(scanner.nextLine());
            if (selectedTime > 0 && selectedTime <= allAvailableSlots.size()) {
                BookingSlots selectedSlot = allAvailableSlots.get(selectedTime - 1);
                System.out.print("Indtast navn på kunden: ");
                String name = scanner.nextLine();
                selectedSlot.status = name;
                System.out.println("Booking bekræftet for " + name + " den " + selectedSlot.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " kl. " + selectedSlot.timeSlot);
            } else {
                System.out.println("Ugyldigt valg. Prøv igen.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ugyldigt valg. Prøv igen.");
        }
    }

    //Metode der sletter booking
    public void deleteBooking() {
        LocalDate date = InputHelper.inputHelperShowDate();
        ArrayList<BookingSlots> bookedSlots = calendar.getDailySlots(date);

        if (bookedSlots.isEmpty()) {
            System.out.println("Ingen bookinger fundet på denne dato.");
            return;
        }

        int counter = 1;
        for (BookingSlots slot : bookedSlots) {
            System.out.println(counter + ". " + slot.timeSlot + " - " + slot.status);
            counter++;
        }

        System.out.print("Vælg en tid (nummer) for at slette bookingen: ");
        try {
            int selectedTime = Integer.parseInt(scanner.nextLine());

            if (selectedTime > 0 && selectedTime <= bookedSlots.size()) {
                BookingSlots selectedSlot = bookedSlots.get(selectedTime - 1);
                selectedSlot.status = "Ledig";
                System.out.println("Booking slettet for " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " kl. " + selectedSlot.timeSlot);
            } else {
                System.out.println("Ugyldigt valg. Prøv igen.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ugyldigt valg. Prøv igen.");
        }
    }
}