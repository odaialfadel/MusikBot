package discord.manage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LiteSQL {
	
		public static Connection conn;
		private static Statement stmt;
		
		
		public static void connect() {
			conn = null;
			
			try {
				File file = new File("datenbank.db");
				if(!file.exists()) {
					file.createNewFile();
				}
				
				String url = "jdbc:sqlite:" + file.getParent();
				conn = DriverManager.getConnection(url);
				
				System.out.println("Verbindung zu Datenbank hergestellt.");
				
				stmt = conn.createStatement();
				
			}catch(IOException | SQLException e) {
				e.printStackTrace();
			} 
		}
		
		public static void disconnect() {
			try {
				if(conn != null) {
					conn.close();
					System.out.println("Verbindung zu Datenbank getrennt.");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		public static void onUpdate(String sql) {
			try {
				stmt.execute(sql);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		public static void onUpdateRAW(String sql) throws SQLException {
			stmt.execute(sql);
		}
		public static ResultSet onQuery(String sql) {
			try {
				return stmt.executeQuery(sql);
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		public static ResultSet onQueryRAW(String sql) throws SQLException {
			return stmt.executeQuery(sql);
		}

}
