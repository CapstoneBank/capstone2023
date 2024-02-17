import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DepositAccountDAO extends SqlExc {

    public int createDepositAccount(DepositAccountDTO account) {
        String sql = "INSERT INTO DepositAccounts (userID, accountNumber, startDate, amount, maturityDate) VALUES (?, ?, ?, ?, ?)";
        return execute(sql, pstmt -> {
            pstmt.setString(1, account.getUserID());
            pstmt.setString(2, account.getAccountNumber());
            pstmt.setDate(3, new java.sql.Date(account.getStartDate().getTime()));
            pstmt.setInt(4, account.getAmount());
            pstmt.setDate(5, new java.sql.Date(account.getMaturityDate().getTime()));
        });
    }

    public List<DepositAccountDTO> getDepositAccountsByUser(String userID) {
        String sql = "SELECT * FROM DepositAccounts WHERE userID = ?";
        return executeQuery(sql, pstmt -> pstmt.setString(1, userID), rs -> {
            List<DepositAccountDTO> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(new DepositAccountDTO(
                        rs.getString("userID"),
                        rs.getString("accountNumber"),
                        rs.getDate("startDate"),
                        rs.getInt("amount"),
                        rs.getDate("maturityDate")
                ));
            }
            return accounts;
        });
    }
    
    public DepositAccountDTO getDepositAccountByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM DepositAccounts WHERE accountNumber = ?";
        return executeQuery(sql, pstmt -> pstmt.setString(1, accountNumber), rs -> {
            if (rs.next()) {
                return new DepositAccountDTO(
                        rs.getString("userID"),
                        rs.getString("accountNumber"),
                        rs.getDate("startDate"),
                        rs.getInt("amount"),
                        rs.getDate("maturityDate")
                );
            }
            return null;
        });
    }
    
    public int deleteExpiredDepositAccounts() {
        // 만기된 예금 통장 삭제
        String sql = "DELETE FROM DepositAccounts WHERE maturityDate <= ?";
        Date currentDate = new Date();
        return execute(sql, pstmt -> pstmt.setDate(1, new java.sql.Date(currentDate.getTime())));
    }
}
