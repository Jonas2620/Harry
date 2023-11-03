import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.nio.file.*;


public class Calendar {
    public ArrayList<BookingSlots> calendar;
    private static final String FILE_NAME = "Harry Calendar.txt";

    //Metode der opretter kalender, medmindre kalender fil eksisterer.
    public Calendar() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            loadCalendar();
        } else {
            calendar = new ArrayList<>();
            createCalendar();
        }
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
        int amount = 200;
        for (int hour = 10; hour < 18; hour++) {
            String timeSlot = String.format("%d:00 - %d:00", hour, hour + 1);
            calendar.add(new BookingSlots(date, timeSlot, status, amount));
        }
    }

    //Metode der viser en valgt dato
    public void showDate() {
        LocalDate date = InputHelper.inputHelperShowDate();

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
        LocalDate date = InputHelper.inputHelperShowDate();

        for (BookingSlots booking : calendar) {
            if (booking.date.equals(date)) {
                booking.status = "Ferie/Fri";
            }
        }
        System.out.println("Ferie/Fri er registreret for " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    //Metode der gemmer kalender
    public void saveCalendar() {
        List<String> lines = new ArrayList<>();
        for (BookingSlots slot : calendar) {
            lines.add(slot.toString());
        }
        try {
            Files.write(Paths.get(FILE_NAME), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Metode der indlæser kalender
    private void loadCalendar() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            calendar = new ArrayList<>();
            for (String line : lines) {
                BookingSlots slot = BookingSlots.parseFromString(line);
                calendar.add(slot);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}