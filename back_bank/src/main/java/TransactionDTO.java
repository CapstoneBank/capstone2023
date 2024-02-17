import java.util.Date;

public class TransactionDTO {
    private String transactionId;
    private String accountId;
    private String fromAccountId;
    private double amount;
    private String transactionType;
    private Date transactionDate;

    // Constructors
    public TransactionDTO() {}

    public TransactionDTO(String transactionId, String accountId, String fromAccountId, double amount, String transactionType, Date transactionDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.fromAccountId = fromAccountId;
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public String getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(String fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}