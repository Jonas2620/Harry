import java.util.Scanner;

public class Menu {
    private final Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void displayMenu() {
        System.out.println("Velkommen til Harry's Salon Booking System");
        System.out.println("1. Booking (Opret/Slet)");
        System.out.println("2. Kalender (Vis kalender/Registrer ferie- eller fridage)");
        System.out.println("3. Økonomi (Generer revisorrapport/Registrer betaling/Tilføj tilkøbte produkter)");
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
        System.out.println("1. Generer revisorrapport");
        System.out.println("2. Registrer betaling");
        System.out.println("3. Tilføj tilkøbte produkter");
        System.out.println("4. Tilbage til hovedmenu");
        System.out.println();
    }

    public void runMenu() {
        boolean running = true;

        while (running) {
            displayMenu();
            String input = getUserInput("Vælg en hovedkategori: ");

            switch (input) {
                case "1": // Hovedmenu 1: "Booking (Opret/Slet)"
                    boolean submenuRunning = true;
                    while (submenuRunning) {
                        displaySubMenu1(); // Vis undermenuen for "Booking"
                        String submenuChoice = getUserInput("Vælg en underkategori: ");
                        switch (submenuChoice) {
                            case "1":
                                //INDSÆT "Opret Booking"-metode
                                break;
                            case "2":
                                // INDSÆT "Slet Booking"-metode
                                break;
                            case "3":
                                submenuRunning = false;
                                break;
                            default:
                                System.out.println("Ugyldigt valg. Prøv igen.");
                                break;
                        }
                    }
                    break;
                case "2": // Hovedmenu 2: "Kalender (Vis kalender/Registrer ferie- eller fridage)"
                    boolean submenuRunning2 = true;
                    while (submenuRunning2) {
                        displaySubMenu2(); // Vis undermenuen for "Kalender"
                        String submenuChoice2 = getUserInput("Vælg en underkategori: ");
                        switch (submenuChoice2) {
                            case "1":
                                //INDSÆT "Vis Kalender"-metode
                                break;
                            case "2":
                                //INDSÆT "Registrer ferie- eller fridage"-metode
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
                case "3": // Hovedmenu 3: "Økonomi (Generer revisorrapport/Registrer Betaling/Tilføj tilkøbte produkter)"
                    boolean submenuRunning3 = true;
                    String Kodeord = getUserInput("Indtast Kodeord: ");
                    if (Kodeord.equals("hairyharry")) {
                        System.out.println();
                        while (submenuRunning3) {
                            displaySubMenu3(); // Vis undermenuen for "Kalender"
                            String submenuChoice3 = getUserInput("Vælg en underkategori: ");
                            switch (submenuChoice3) {
                                case "1":
                                    //INDSÆT "Generer revisorraport"-metode
                                    break;
                                case "2":
                                    //INDSÆT "Registrer Betaling"-metode
                                    break;
                                case "3":
                                    //INDSÆT "Tilføj tilkøbte produkter"-metode
                                    break;
                                case "4":
                                    submenuRunning3 = false;
                                    break;
                                default:
                                    System.out.println("Ugyldigt valg. Prøv igen.");
                                    break;
                            }
                        }

                    }
                    else
                        System.out.println("Forkert kodeord/n");
                    submenuRunning3 = false;
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
