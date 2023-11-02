import java.time.LocalDate;

public class Menu {
    private final Calendar calendar;
    private final Booking booking;
    private final Economy economy;

    public Menu() {
        calendar = new Calendar();
        booking = new Booking(calendar);
        economy = new Economy(calendar);
    }

    public void displayMenu() {
        System.out.println("Harry's Salon - Bookingsystem");
        System.out.println("1. Booking (Opret/Slet)");
        System.out.println("2. Kalender (Vis kalender/Registrer ferie- eller fridage)");
        System.out.println("3. Økonomi (Tilføj tilkøbte produkter/Registrer kredit/Slet kredit/Revisorrapport)");
        System.out.println("4. Afslut");
        System.out.println();
    }

    public void displaySubMenu1() {
        System.out.println("Booking:");
        System.out.println("1. Opret aftale");
        System.out.println("2. Slet aftale");
        System.out.println("3. Tilbage til hovedmenu");
        System.out.println();
    }

    public void displaySubMenu2() {
        System.out.println("Kalender:");
        System.out.println("1. Vis dato i kalenderen");
        System.out.println("2. Registrer ferie- eller fridage");
        System.out.println("3. Tilbage til hovedmenu");
        System.out.println();
    }

    public void displaySubMenu3() {
        System.out.println("Økonomi og Rapporter:");
        System.out.println("1. Tilføj tilkøbte produkter");
        System.out.println("2. Registrer kredit");
        System.out.println("3. Slet kredit");
        System.out.println("4. Generer revisorrapport");
        System.out.println("5. Tilbage til hovedmenu");
        System.out.println();
    }

    public void runMenu() {
        boolean running = true;

        while (running) {
            displayMenu();
            String input = InputHelper.getUserInput("Vælg en hovedkategori: ");
            System.out.println();

            switch (input) {
                case "1": // Hovedmenu 1: "Booking (Opret/Slet)"
                    boolean submenuRunning = true;
                    while (submenuRunning) {
                        displaySubMenu1();
                        String submenuChoice = InputHelper.getUserInput("Vælg en underkategori: ");
                        switch (submenuChoice) {
                            case "1":
                               booking.createBooking();
                                break;
                            case "2":
                                booking.deleteBooking();
                                break;
                            case "3":
                                submenuRunning = false;
                                break;
                            default:
                                System.out.println("Ugyldigt valg. Prøv igen.");
                                break;
                        }
                        System.out.println();
                    }
                    break;
                case "2": // Hovedmenu 2: "Kalender (Vis kalender/Registrer ferie- eller fridage)"
                    boolean submenuRunning2 = true;
                    while (submenuRunning2) {
                        displaySubMenu2();
                        String submenuChoice2 = InputHelper.getUserInput("Vælg en underkategori: ");
                        switch (submenuChoice2) {
                            case "1":
                                calendar.showDate();
                                break;
                            case "2":
                                calendar.registerHoliday();
                                break;
                            case "3":
                                submenuRunning2 = false;
                                break;
                            default:
                                System.out.println("Ugyldigt valg. Prøv igen.");
                                break;
                        }
                        System.out.println();
                    }
                    break;
                case "3": // Hovedmenu 3: "Økonomi (Generer revisorrapport/Registrer Betaling/Tilføj tilkøbte produkter)"
                    boolean submenuRunning3 = true;
                    String Password = InputHelper.getUserInput("Indtast Kodeord: ");
                    if (Password.equals("hairyharry")) {
                        System.out.println();
                        while (submenuRunning3) {
                            displaySubMenu3();
                            String submenuChoice3 = InputHelper.getUserInput("Vælg en underkategori: ");
                            switch (submenuChoice3) {
                                case "1":
                                    economy.addPurchasedProducts();
                                    break;
                                case "2":
                                    economy.markAsCredit();
                                    break;
                                case "3":
                                    economy.deleteCredit();
                                    break;
                                case "4":
                                    economy.dailyEconomicReport();
                                    break;
                                case "5":
                                    submenuRunning3 = false;
                                    break;
                                default:
                                    System.out.println("Ugyldigt valg. Prøv igen.");
                                    break;
                            }
                            System.out.println();
                        }
                    }
                    else
                        System.out.println("Forkert kodeord/n");
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