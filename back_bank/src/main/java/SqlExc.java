import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlExc {
	public int execute(String sql, SQLConsumer<PreparedStatement> consumer) {
	    int ret = 0;
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = getConnection();
	        System.out.println("connection ok");
	        pstmt = conn.prepareStatement(sql);

	        consumer.accept(pstmt);  // 여기서 매개변수로 전달받은 로직을 수행합니다.

	        pstmt.executeUpdate();
	        ret = 1;
	    } catch (SQLException e) {
	        ret = -1;
	        System.out.println("access error.");
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    } finally {
	        closeConnection(null, pstmt, conn);
	    }
	    return ret;
	}
	
	
	public Connection getConnection() {
		
		Connection conn=null;		
		String DBName = "cbank";
		String dbURL = "jdbc:mysql://localhost:3306/" + DBName;
		String sslStr="?useSSL=false";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			System.out.println("JDBC driver load success");

			conn = DriverManager.getConnection(dbURL+sslStr
					, "root","0221"); 			
			System.out.println("DB connection success");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC driver load fail !!");
		} catch (SQLException e) {
			System.out.println("DB connection fail !!");
		}
		
		return conn;
	}
	
	public void closeConnection(ResultSet set, PreparedStatement pstmt, Connection connection) {
		if(set!=null)
		{
			try {
			set.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		if(pstmt!=null)
		{
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if(connection!=null)
		{
			try {
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
    public <R> R executeQuery(String sql, SQLConsumer<PreparedStatement> consumer, SQLFunction<ResultSet, R> mapper) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        R result = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            consumer.accept(pstmt);

            rs = pstmt.executeQuery();
            result = mapper.apply(rs);

        } catch (SQLException e) {
            System.out.println("access error.");
            e.printStackTrace();
        } finally {
            closeConnection(rs, pstmt, conn);
        }

        return result;
    }
}

@FunctionalInterface
interface SQLFunction<T, R> {
    R apply(T t) throws SQLException;
}

@FunctionalInterface
interface SQLConsumer<T> {
    void accept(T t) throws SQLException;
}
