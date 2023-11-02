import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class BookingSlots {
    public LocalDate date;
    public String timeSlot;
    public String status;
    public int amount;

    public BookingSlots(LocalDate date, String timeSlot, String status, int amount) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE) + "," +
                timeSlot + "," +
                status + "," +
                amount;
    }

    public static BookingSlots parseFromString(String line) {
        String[] parts = line.split(",");
        LocalDate date = LocalDate.parse(parts[0]);
        String timeSlot = parts[1];
        String status = parts[2];
        int amount = Integer.parseInt(parts[3]);
        return new BookingSlots(date, timeSlot, status, amount);
    }
}