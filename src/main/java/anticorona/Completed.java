package anticorona;

public class Completed extends AbstractEvent {

    private Long injectionId;
    private Long bookingId​;
    private Long userId​;
    private Long vaccineId;
    private String status;

    public Long getInjectionId() {
        return injectionId;
    }

    public void setInjectionId(Long injectionId) {
        this.injectionId = injectionId;
    }
    public Long getBookingId() {
        return bookingId​;
    }

    public void setBookingId(Long bookingId​) {
        this.bookingId​ = bookingId​;
    }
    public Long getUserId() {
        return userId​;
    }

    public void setUserId(Long userId​) {
        this.userId​ = userId​;
    }
    public Long getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Long vaccineId) {
        this.vaccineId = vaccineId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}