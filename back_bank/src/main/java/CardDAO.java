import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDAO extends SqlExc {
    
    // 카드를 생성하는 함수
    public int createCard(CardDTO card) {
        String sql = "insert into card values (?, ?, ?, ?)";
        return execute(sql, statement -> {
            statement.setString(1, card.getCardId());
            statement.setString(2, card.getUserId());
            statement.setString(3, card.getCardType());
            statement.setString(4, card.getAccountId()); // Updated to use getAccountId()
        });
    }
    
    // 카드를 생성한 후 변경된 내용을 덮어쓰는 함수
    public int updateCard(CardDTO card) {
        String sql = "update card set userId = ?, cardType = ?, AccountId = ? where cardId = ?";
        return execute(sql, statement -> {
            statement.setString(1, card.getUserId());
            statement.setString(2, card.getCardType());
            statement.setString(3, card.getAccountId()); // Updated to use getAccountId()
            statement.setString(4, card.getCardId());
        });
    }
    
    // 카드 안에 내용(userId 등) 삭제
    public int deleteCard(String cardId) {
        String sql = "delete from card where cardId = ?";
        return execute(sql, statement -> {
            statement.setString(1, cardId);
        });
    }
    
    // 카드 검색, 정보 제공에 필요한 기능
    public CardDTO getCard(String cardId) {
        String sql = "select * from card where cardId = ?";
        CardDTO card = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cardId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("userId");
                String cardType = rs.getString("cardType");
                String accountId = rs.getString("AccountId"); // Updated to use AccountId
                card = new CardDTO(cardId, userId, cardType, accountId); // Updated constructor
            }
        } catch (SQLException e) {
            System.out.println("access error");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(rs, pstmt, conn);
        }

        return card;
    }
    
    // 말 그대로 겟메소드임
    public ArrayList<CardDTO> getCardsByUserAndType(String userId) {
        ArrayList<CardDTO> cards = new ArrayList<>();
        String sql = "SELECT * FROM card WHERE userId=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String cardId = resultSet.getString("cardId");
                String cardType = resultSet.getString("cardType");
                String accountId = resultSet.getString("AccountId"); // Updated to use AccountId
                cards.add(new CardDTO(cardId, userId, cardType, accountId)); // Updated constructor
            }
        } catch (SQLException e) {
            System.out.println("access error");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, pstmt, conn);
        }
        return cards;
    }

    public List<CardDTO> getCardsByUserAndType(String userId, String cardType) {
        List<CardDTO> cards = new ArrayList<>();
        String sql = "SELECT * FROM card WHERE userId=? AND cardType=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, cardType);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String cardId = resultSet.getString("cardId");
                String dbUserId = resultSet.getString("userId");
                String dbCardType = resultSet.getString("cardType");
                String accountId = resultSet.getString("AccountId"); // Updated to use AccountId
                cards.add(new CardDTO(cardId, dbUserId, dbCardType, accountId)); // Updated constructor
            }
        } catch (SQLException e) {
            System.out.println("접근 오류");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, pstmt, conn);
        }
        return cards;
    }
}
