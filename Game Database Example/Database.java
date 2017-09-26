package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Database {
	private final String user = "postgres", 
			password = "admin";
	private String url = "jdbc:postgresql://localhost/game";
	private boolean connected = false;
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;
	//

	
	Database(String url){
		this.url = url;
	}
	
	protected void open() {
		if (con == null){
			try {
				con = DriverManager.getConnection(url, user, password);
				st = con.createStatement();
				connected = true;

			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	private void close(){
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
			connected = false;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	
	public synchronized ResultSet selectQuery(String query){
		open();
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		//close();
		return rs;
	}
	/*
	protected boolean hasReturnedRow(String query){
		rs = selectQuery(query);
		try {
			if (rs.next()) return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	*/
	
	//INSERT statements too
	protected synchronized void updateQuery(String query){
		open();
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		//close();
	}


}
