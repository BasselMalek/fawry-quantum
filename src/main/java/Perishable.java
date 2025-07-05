import java.time.LocalDate;

public interface Perishable {

    public LocalDate getExpiryDate();
    public Boolean isExpired();

}
