import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingSlots {
    public final LocalDate date;
    public final String timeSlot;
    public String status;
    public int amount;

    //BookingSlots objekter som er det parameter calendar benytter
    public BookingSlots(LocalDate date, String timeSlot, String status, int amount) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
        this.amount = amount;
    }

    //Metode til at lave stringrepræsentation ud af objekterne.
    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE) + "," +
                timeSlot + "," +
                status + "," +
                amount;
    }
    //Metode der tager en String og deler den op i mindre dele og
    public static BookingSlots parseFromString(String line) {
        String[] parts = line.split(",");
        LocalDate date = LocalDate.parse(parts[0]);
        String timeSlot = parts[1];
        String status = parts[2];
        int amount = Integer.parseInt(parts[3]);
        return new BookingSlots(date, timeSlot, status, amount);
    }
}