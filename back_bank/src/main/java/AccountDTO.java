public class AccountDTO {
    private String accountId;
    private String userId;
    private String accountType;
    private double balance;

    // Constructors
    public AccountDTO() {}

    public AccountDTO(String accountId, String userId, String accountType, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Getters and Setters
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}