import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserDAO extends SqlExc{
	

	public int createUser(UserDTO user) {
	    String sql = "insert into User values (?, ?)";
	    return execute(sql, statement -> {
	        statement.setString(1, user.getUserId());
	        statement.setString(2, user.getPassword());
	    });
	}
	
	public int updateUser(UserDTO user) {
		 String sql = "update User set password = ? where userId = ? ";
		    return execute(sql, statement -> {

		        statement.setString(2, user.getUserId());
		        statement.setString(1, user.getPassword());
		    });
		
	}
	
	// deleteUser method
	public int deleteUser(String userId) {
	    String sql = "delete from User where userId = ?";
	    return execute(sql, statement -> {
	        statement.setString(1, userId);
	    });
	}
	
	public UserDTO getUser(String userId) {
	    String sql = "select * from User where userId = ?";
	    UserDTO user = null;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = getConnection();
	        System.out.println("connection ok");
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, userId);

	        rs = pstmt.executeQuery();
	        
	        if(rs.next()) {
	            String retrievedUserId = rs.getString("userId");
	            String password = rs.getString("password");
	            user = new UserDTO(retrievedUserId, password);
	        }

	    } catch (SQLException e) {
	        System.out.println("access error.");
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    } finally {
	        closeConnection(rs, pstmt, conn);
	    }
	    
	    return user;
	}
	
}