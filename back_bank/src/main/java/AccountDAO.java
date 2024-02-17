
import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends SqlExc {
	public static void main(String[] args) {
		 AccountDAO accountDAO = new AccountDAO();
		 accountDAO.createAccount(new AccountDTO("dfsss", "dksdfdfgdgdfs", "dfss", 1));
	}

    public int createAccount(AccountDTO account) {
        String sql = "INSERT INTO account (accountId, userId, accountType, balance) VALUES (?, ?, ?, ?)";
        return execute(sql, pstmt -> {
            pstmt.setString(1, account.getAccountId());
            pstmt.setString(2, account.getUserId());
            pstmt.setString(3, account.getAccountType());
            pstmt.setDouble(4, account.getBalance());
        });
    }

    public int updateAccount(AccountDTO account) {
        String sql = "UPDATE account SET userId = ?, accountType = ?, balance = ? WHERE accountId = ?";
        return execute(sql, pstmt -> {
            pstmt.setString(1, account.getUserId());
            pstmt.setString(2, account.getAccountType());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setString(4, account.getAccountId());
        });
    }

    public int deleteAccount(String accountId) {
        String sql = "DELETE FROM account WHERE accountId = ?";
        return execute(sql, pstmt -> pstmt.setString(1, accountId));
    }

    public AccountDTO getAccount(String accountId) {
        String sql = "SELECT * FROM account WHERE accountId = ?";
        return executeQuery(sql, pstmt -> pstmt.setString(1, accountId), rs -> {
            if (rs.next()) {
                return new AccountDTO(
                        rs.getString("accountId"),
                        rs.getString("userId"),
                        rs.getString("accountType"),
                        rs.getDouble("balance")
                );
            }
            return null;
        });
    }

    public List<AccountDTO> getAccountsByUser(String userId) {
        String sql = "SELECT * FROM account WHERE userId = ?";
        return executeQuery(sql, pstmt -> pstmt.setString(1, userId), rs -> {
            List<AccountDTO> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(new AccountDTO(
                        rs.getString("accountId"),
                        rs.getString("userId"),
                        rs.getString("accountType"),
                        rs.getDouble("balance")
                ));
            }
            return accounts;
        });
    }
}
