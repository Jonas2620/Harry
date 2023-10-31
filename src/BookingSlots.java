import java.time.LocalDate;

class BookingSlots {
    public LocalDate date;
    public String timeSlot;
    public String status;

    public BookingSlots(LocalDate date, String timeSlot, String status) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
    }
}