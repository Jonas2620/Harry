public class Menu {
    private final Calendar calendar;
    private final Booking booking;
    private final Economy economy;

    public Menu() {
        calendar = new Calendar();
        booking = new Booking(calendar);
        economy = new Economy(calendar);
    }

    //Hovedmenu
    public void displayMenu() {
        System.out.println("Harry's Salon - Bookingsystem");
        System.out.println("1. Booking (Opret/Slet)");
        System.out.println("2. Kalender (Vis kalender/Registrer ferie- eller fridage)");
        System.out.println("3. Økonomi (Tilføj tilkøbte produkter/Registrer kredit/Slet kredit/Revisorrapport)");
        System.out.println("4. Gem og Afslut");
        System.out.println("x. Annuller og Afslut");
        System.out.println();
    }

    //Undermenu 1: Booking
    public void displaySubMenu1() {
        System.out.println("Booking:");
        System.out.println("1. Opret aftale");
        System.out.println("2. Slet aftale");
        System.out.println("3. Tilbage til hovedmenu");
        System.out.println();
    }

    //Undermenu 2: Kalender
    public void displaySubMenu2() {
        System.out.println("Kalender:");
        System.out.println("1. Vis dato i kalenderen");
        System.out.println("2. Registrer ferie- eller fridage");
        System.out.println("3. Tilbage til hovedmenu");
        System.out.println();
    }

    //Undermenu 3: Økonomi og rapporter
    public void displaySubMenu3() {
        System.out.println("Økonomi og Rapporter:");
        System.out.println("1. Tilføj tilkøbte produkter");
        System.out.println("2. Registrer kredit");
        System.out.println("3. Slet kredit");
        System.out.println("4. Generer revisorrapport");
        System.out.println("5. Tilbage til hovedmenu");
        System.out.println();
    }

    //runMenu starter GUI og styrer brugerens interageren med systemet
    public void runMenu() {
        boolean running = true;

        while (running) {
            displayMenu();
            String input = InputHelper.getUserInput("Vælg en hovedkategori: ");
            System.out.println();

            switch (input) {
                case "1" -> { // Hovedmenu 1: "Booking (Opret/Slet)"
                    boolean submenuRunning = true;
                    while (submenuRunning) {
                        displaySubMenu1();
                        String submenuChoice = InputHelper.getUserInput("Vælg en underkategori: ");
                        switch (submenuChoice) {
                            case "1" -> booking.createBooking();
                            case "2" -> booking.deleteBooking();
                            case "3" -> submenuRunning = false;
                            default -> System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                        System.out.println();
                    }
                }
                case "2" -> { // Hovedmenu 2: "Kalender (Vis kalender/Registrer ferie- eller fridage)"
                    boolean submenuRunning2 = true;
                    while (submenuRunning2) {
                        displaySubMenu2();
                        String submenuChoice2 = InputHelper.getUserInput("Vælg en underkategori: ");
                        switch (submenuChoice2) {
                            case "1" -> calendar.showDate();
                            case "2" -> calendar.registerHoliday();
                            case "3" -> submenuRunning2 = false;
                            default -> System.out.println("Ugyldigt valg. Prøv igen.");
                        }
                        System.out.println();
                    }
                }
                case "3" -> { // Hovedmenu 3: "Økonomi (Generer revisorrapport/Registrer Betaling/Tilføj tilkøbte produkter)"
                    boolean submenuRunning3 = true;
                    String Password = InputHelper.getUserInput("Indtast Kodeord: ");
                    if (Password.equals("hairyharry")) {
                        System.out.println();
                        while (submenuRunning3) {
                            displaySubMenu3();
                            String submenuChoice3 = InputHelper.getUserInput("Vælg en underkategori: ");
                            switch (submenuChoice3) {
                                case "1" -> economy.addPurchasedProducts();
                                case "2" -> economy.markAsCredit();
                                case "3" -> economy.deleteCredit();
                                case "4" -> economy.dailyEconomicReport();
                                case "5" -> submenuRunning3 = false;
                                default -> System.out.println("Ugyldigt valg. Prøv igen.");
                            }
                            System.out.println();
                        }
                    } else
                        System.out.println("Forkert kodeord\n");
                }
                case "4" -> { // Hovedmenu 4: "Gem og afslut"
                    running = false;
                    calendar.saveCalendar();
                    System.out.println("Ændringerne er gemt og programmet afsluttes");
                }
                case "x" -> { // "Annuller og afslut"
                    System.out.println("Ændringerne er annulleret og programmet afsluttes");
                    running = false;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }
}