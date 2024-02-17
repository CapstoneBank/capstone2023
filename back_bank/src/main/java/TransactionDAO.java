import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends SqlExc {

    // 거래 생성
    public int createTransaction(TransactionDTO transaction) {
        String sql = "insert into Transaction (transactionId, accountId, fromAccountId, amount, transactionType, transactionDate) values (?, ?, ?, ?, ?, ?)";
        return execute(sql, statement -> {
            statement.setString(1, transaction.getTransactionId());
            statement.setString(2, transaction.getAccountId());
            statement.setString(3, transaction.getFromAccountId());
            statement.setDouble(4, transaction.getAmount());
            statement.setString(5, transaction.getTransactionType());
            statement.setDate(6, new java.sql.Date(transaction.getTransactionDate().getTime()));
        });
    }

    public List<TransactionDTO> getTransactionsByAccount(String accountId) {
        String sql = "select * from Transaction where accountId = ?";
        return this.executeQuery(sql, statement -> {
            statement.setString(1, accountId);
        }, rs -> {
            List<TransactionDTO> transactions = new ArrayList<>();
            while (rs.next()) {
                String transactionId = rs.getString("transactionId");
                String fromAccountId = rs.getString("fromAccountId");
                Double amount = rs.getDouble("amount");
                String transactionType = rs.getString("transactionType");
                Date transactionDate = rs.getDate("transactionDate");
                transactions.add(new TransactionDTO(transactionId, accountId, fromAccountId, amount, transactionType, transactionDate));
            }
            return transactions;
        });
    }
}
