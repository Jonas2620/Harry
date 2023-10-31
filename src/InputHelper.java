import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);
    public static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    public static LocalDate inputHelperDate() {
        String input = getUserInput("Indtast ønskede dato (dd-MM-yyyy): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(input, formatter);
        System.out.println();
        return date;
    }
}

