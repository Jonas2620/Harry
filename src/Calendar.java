import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Calendar {
    public ArrayList<BookingSlots> calendar;
    private final Scanner scanner;

    public Calendar() {
        calendar = new ArrayList<>();
        createCalendar();
        this.scanner = new Scanner(System.in);
    }

    //Metode der laver kalender
    private void createCalendar() {
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String status = isWeekend(date) ? "Lukket (Weekend)" : "Ledig";
            createDailySlots(date, status);
        }
    }

    //Metode der tjekker om dagen er en Weekenddag
    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    //Metode der laver timeslots af en times varighed, startende fra kl.10
    private void createDailySlots(LocalDate date, String status) {
        for (int hour = 10; hour < 18; hour++) {
            String timeSlot = String.format("%d:00 - %d:00", hour, hour + 1);
            calendar.add(new BookingSlots(date, timeSlot, status));
        }
    }

    //Metode der viser en valgt dato
    public void showDate() {
        LocalDate date = InputHelper.inputHelperDate();

        System.out.println("Dato "+date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ":");
        for (BookingSlots booking : calendar) {
            if (booking.date.equals(date)) {
                System.out.println(booking.timeSlot + " - " + booking.status);
            }
        }
    }

    //Metode der viser timeslots
    public ArrayList<BookingSlots> getDailySlots(LocalDate date) {
        ArrayList<BookingSlots> dailySlots = new ArrayList<>();
        for (BookingSlots booking : calendar) {
            if (booking.date.equals(date)) {
                dailySlots.add(booking);
            }
        }
        return dailySlots;
    }

    //Metode der markerer ferie- fridage
    public void registerHoliday() {
        LocalDate date = InputHelper.inputHelperDate();

        for (BookingSlots booking : calendar) {
            if (booking.date.equals(date)) {
                booking.status = "Ferie/Fri";
            }
        }
        System.out.println("Ferie/Fri er registreret for " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }
}