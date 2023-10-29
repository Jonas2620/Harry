import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.TreeMap;

public class Booking {
    private final Calendar calendar;
    private final Scanner scanner;

    public Booking(Calendar calendar) {
        this.calendar = calendar;
        this.scanner = new Scanner(System.in);
    }

    public void createBooking() {
        System.out.print("Indtast ønsket dato (dd-MM-yyyy): ");
        String dateStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(dateStr, formatter);
        System.out.println();

        TreeMap<Integer, String> timeSlotMap = new TreeMap<>();
        int counter = 1;

        for (int i = 0; i < 5; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            TreeMap<String, String> dailySlots = calendar.getDailySlots(currentDate);
            if (dailySlots != null) {
                System.out.println("Dato: " + currentDate.format(formatter) + ":");
                for (String timeSlot : dailySlots.keySet()) {
                    System.out.println(counter + ". " + timeSlot + " - " + dailySlots.get(timeSlot));
                    timeSlotMap.put(counter, currentDate + "|" + timeSlot);
                    counter++;
                }
            }
            System.out.println();
        }

        System.out.print("Vælg en tid (nummer): ");
        int selectedTime = Integer.parseInt(scanner.nextLine());

        String selectedTimeSlotInfo = timeSlotMap.get(selectedTime);
        if (selectedTimeSlotInfo == null) {
            System.out.println("Ugyldigt valg. Prøv igen.");
            return;
        }

        String[] parts = selectedTimeSlotInfo.split("\\|");
        LocalDate selectedDate = LocalDate.parse(parts[0]);
        String selectedTimeSlot = parts[1];

        TreeMap<String, String> selectedDailySlots = calendar.getDailySlots(selectedDate);
        if (!"Ledig".equals(selectedDailySlots.get(selectedTimeSlot))) {
            System.out.println("Dette tidspunkt er allerede booket. Vælg et andet.");
            return;
        }

        System.out.print("Indtast navn på kunden: ");
        String name = scanner.nextLine();

        selectedDailySlots.put(selectedTimeSlot, name);
        System.out.println("Booking bekræftet for " + name + " den " + selectedDate.format(formatter) + " kl. " + selectedTimeSlot);
    }

    public void deleteBooking() {
            System.out.print("Indtast dato for bookingen, du vil slette (dd-MM-yyyy): ");
            String dateStr = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate selectedDate = LocalDate.parse(dateStr, formatter);
            System.out.println();

            TreeMap<String, String> dailySlots = calendar.getDailySlots(selectedDate);
            if (dailySlots == null) {
                System.out.println("Ingen bookinger fundet på denne dato.");
                return;
            }

            System.out.println("Tilgængelige bookinger på " + selectedDate.format(formatter) + ":");
            int counter = 1;
            TreeMap<Integer, String> timeSlotMap = new TreeMap<>();
            for (String timeSlot : dailySlots.keySet()) {
                String status = dailySlots.get(timeSlot);
                if (!"Ledig".equals(status)) {
                    System.out.println(counter + ". " + timeSlot + " - " + status);
                    timeSlotMap.put(counter, timeSlot);
                    counter++;
                }
            }

            if (timeSlotMap.isEmpty()) {
                System.out.println("Ingen bookinger fundet på denne dato.");
                return;
            }

            System.out.print("Vælg en tid (nummer) for at slette bookingen: ");
            int selectedTime = Integer.parseInt(scanner.nextLine());

            String selectedTimeSlot = timeSlotMap.get(selectedTime);
            if (selectedTimeSlot == null) {
                System.out.println("Ugyldigt valg. Prøv igen.");
                return;
            }

            dailySlots.put(selectedTimeSlot, "Ledig");
            System.out.println("Booking slettet for " + selectedDate.format(formatter) + " kl. " + selectedTimeSlot);
        }
    }