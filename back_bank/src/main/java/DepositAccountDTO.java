
import java.util.Date;

public class DepositAccountDTO {
    private String userID;
    private String accountNumber;
    private Date startDate;
    private int amount;
    private Date maturityDate;

    public DepositAccountDTO() {}

    public DepositAccountDTO(String userID, String accountNumber, Date startDate, int amount, Date maturityDate) {
        this.userID = userID;
        this.accountNumber = accountNumber;
        this.startDate = startDate;
        this.amount = amount;
        this.maturityDate = maturityDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    @Override
    public String toString() {
        return "DepositAccountDTO{" +
                "userID='" + userID + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", startDate=" + startDate +
                ", amount=" + amount +
                ", maturityDate=" + maturityDate +
                '}';
    }
}
