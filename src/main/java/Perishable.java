import java.util.Date;

public interface Perishable {

    public Date getExpiryDate();
    public Boolean isExpired();

}
