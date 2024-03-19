package connectDataBase;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class ConnectionData {
	public static Connection accessDataBase() {
		Connection con = null;
		try {
			String url = "jdbc:sqlserver://localhost:1433;databasename=QLHTT;encryty=false;";
			String user = "user";
			String passWord = "123";
			con = DriverManager.getConnection(url, user, passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return con;
		
	}
}
