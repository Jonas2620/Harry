import java.time.LocalDate;
import java.time.format.*;
import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);
    public static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static LocalDate inputHelperShowDate() {
        LocalDate date = null;
        while (date == null) {
            try {
                String input = getUserInput("Indtast ønskede dato (dd-MM-yyyy): ");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                date = LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Ugyldigt datoformat. Prøv igen.");
            }
        }
        System.out.println();
        return date;
    }
}