import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Menu {
    private final Scanner scanner;
    private final Calendar calendar;

    public Menu() {
        scanner = new Scanner(System.in);
        calendar = new Calendar();
    }

    public void displayMenu() {
        System.out.println("Velkommen til Harry's Salon Booking System");
        System.out.println("1. Håndtering af frisøraftaler (Opret/Slet)");
        System.out.println("2. Kalender (Vis kalender/Registrér ferie- eller fridage)");
        System.out.println("3. Økonomi og rapporter (Generér revisorrapport/Registrér betaling/Tilføj tilkøbte produkter)");
        System.out.println("4. Afslut");
        System.out.println();
    }

    public void displaySubMenu1() {
        System.out.println("Undermenu for Håndtering af frisøraftaler:");
        System.out.println("1. Opret aftale");
        System.out.println("2. Slet aftale");
        System.out.println("3. Tilbage til hovedmenu");
        System.out.println();
    }

    public void displaySubMenu2() {
        System.out.println("Undermenu for Kalender:");
        System.out.println("1. Vis dato i kalenderen");
        System.out.println("2. Registrér ferie- eller fridage");
        System.out.println("3. Tilbage til hovedmenu");
        System.out.println();
    }


    public void handleCreateAppointment() {
        String dateStr = getUserInput("Indtast ønsket dato (dd-MM-yyyy): ");
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.println();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Lav en liste til at gemme de næste 5 dage inklusive den valgte dato
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dates.add(date.plusDays(i));
        }

        // Lav en liste til at gemme ledige tidspunkter for hver dag
        List<List<String>> availableTimeSlotsList = new ArrayList<>();

        // Loop gennem hver dag og indsamle de ledige tidspunkter
        for (LocalDate currentDate : dates) {
            TreeMap<String, String> dailySlots = calendar.getDailySlots(currentDate);

            if (dailySlots == null) {
                System.out.println(currentDate.format(formatter) + ": Lukket");
                continue;
            }

            AtomicBoolean slotsAvailable = new AtomicBoolean(false);
            System.out.println("Ledige tidspunkter for " + currentDate.format(formatter) + ":");

            // Opret en liste af ledige tidspunkter
            List<String> availableTimeSlots = new ArrayList<>();
            AtomicInteger counter = new AtomicInteger(1);
            dailySlots.forEach((timeSlot, availability) -> {
                if (availability.equals("Available")) {
                    availableTimeSlots.add(timeSlot);
                    System.out.println(counter.getAndIncrement() + ". " + timeSlot);
                    slotsAvailable.set(true);
                }
            });

            if (!slotsAvailable.get()) {
                System.out.println("Ingen ledige tider");
            }

            availableTimeSlotsList.add(availableTimeSlots);
            System.out.println();
        }

        // Venter på brugerens input for at vælge tidspunkt og dato
        int chosenDay = Integer.parseInt(getUserInput("Vælg en dag (1-5): "));
        if (chosenDay >= 1 && chosenDay <= dates.size()) {
            int chosenSlot = Integer.parseInt(getUserInput("Vælg et nummer for at vælge tidspunkt: "));
            if (chosenSlot >= 1 && chosenSlot <= availableTimeSlotsList.get(chosenDay - 1).size()) {
                LocalDate chosenDate = dates.get(chosenDay - 1);
                String chosenTime = availableTimeSlotsList.get(chosenDay - 1).get(chosenSlot - 1);
                handleCreateBooking(chosenDate, chosenTime);
            } else {
                System.out.println("Ugyldigt valg. Prøv igen.");
            }
        } else {
            System.out.println("Ugyldigt valg af dag. Prøv igen.");
        }
    }

    public void handleCreateBooking(LocalDate date, String chosenTime) {
        Booking booking = new Booking(calendar);
        booking.makeBooking(date, chosenTime);
        System.out.println();
    }

    public void handleDeleteAppointment() {
        String dateStr = getUserInput("Indtast datoen for den aftale, du vil slette (dd-MM-yyyy): ");
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.println();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Vis de nuværende bookede tider for den valgte dato
        TreeMap<String, String> bookedTimeSlots = calendar.getDailySlots(date);

        if (bookedTimeSlots == null) {
            System.out.println(date.format(formatter) + ": Der er ingen bookede tider på denne dato.");
            return;
        }

        System.out.println("Bookede tidspunkter for " + date.format(formatter) + ":");
        AtomicInteger counter = new AtomicInteger(1);
        List<String> slotsToDelete = new ArrayList<>();

        bookedTimeSlots.forEach((timeSlot, availability) -> {
            if (availability.equals("Booked")) {
                System.out.println(counter.getAndIncrement() + ". " + timeSlot);
                slotsToDelete.add(timeSlot);
            }
        });

        if (slotsToDelete.isEmpty()) {
            System.out.println("Ingen bookede tider at slette.");
            return;
        }

        int chosenSlot = Integer.parseInt(getUserInput("Vælg et nummer for at slette tidspunkt: "));
        if (chosenSlot >= 1 && chosenSlot <= slotsToDelete.size()) {
            String timeToDelete = slotsToDelete.get(chosenSlot - 1);
            calendar.deleteBooking(date, timeToDelete);
            System.out.println("Tidspunktet " + timeToDelete + " er blevet slettet.");
        } else {
            System.out.println("Ugyldigt valg. Prøv igen.");
        }
        System.out.println();
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void handleViewDateInCalendar() {
        String dateStr = getUserInput("Indtast ønsket dato (dd-MM-yyyy): ");
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Vis datoen og dens tilgængelighed i kalenderen
        TreeMap<String, String> dailySlots = calendar.getDailySlots(date);

        if (dailySlots == null) {
            System.out.println(date.format(formatter) + ": Lukket");
        } else {
            System.out.println(date.format(formatter) + ":");
            dailySlots.forEach((timeSlot, availability) -> System.out.println(timeSlot + " - " + availability));
        }
        System.out.println();
    }

    public void handleRegisterHoliday() {
        String dateStr = getUserInput("Indtast dato for ferie- eller fridag (dd-MM-yyyy): ");
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        // Registrer ferie- eller fridag i kalenderen
        calendar.registerHoliday(date);
    }


    public static void main(String[] args) {
        Menu menu = new Menu();
        boolean running = true;

        while (running) {
            menu.displayMenu();
            String input = menu.getUserInput("Vælg en hovedkategori: ");

            switch (input) {
                case "1":
                    // Hovedmenu 1: Håndtering af frisøraftaler (Opret/Slet)
                    boolean submenuRunning = true;
                    while (submenuRunning) {
                        menu.displaySubMenu1(); // Vis undermenuen for "Håndtering af frisøraftaler (Opret/Slet)"
                        String submenuChoice = menu.getUserInput("Vælg en underkategori: ");
                        switch (submenuChoice) {
                            case "1":
                                menu.handleCreateAppointment();
                                break;
                            case "2":
                                menu.handleDeleteAppointment();
                                break; // Tilføjelse: Slet aftale
                            case "3":
                                submenuRunning = false;
                                break;
                            default:
                                System.out.println("Ugyldigt valg. Prøv igen.");
                                break;
                        }
                    }
                    break;
                case "2":
                    // Hovedmenu 2: Kalender (Vis kalender/Registrér ferie- eller fridage)
                    boolean submenuRunning2 = true;
                    while (submenuRunning2) {
                        menu.displaySubMenu2(); // Vis undermenuen for "Kalender"
                        String submenuChoice2 = menu.getUserInput("Vælg en underkategori: ");
                        switch (submenuChoice2) {
                            case "1":
                                menu.handleViewDateInCalendar();
                                break;
                            case "2":
                                menu.handleRegisterHoliday();
                                break;
                            case "3":
                                submenuRunning2 = false;
                                break;
                            default:
                                System.out.println("Ugyldigt valg. Prøv igen.");
                                break;
                        }
                    }
                    break;
                case "3":
                    // Implementer logik for hovedmenu 3 (Økonomi og rapporter)
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
                    break;
            }
        }
    }
}
