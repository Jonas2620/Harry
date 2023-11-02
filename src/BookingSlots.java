import java.time.LocalDate;

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
}