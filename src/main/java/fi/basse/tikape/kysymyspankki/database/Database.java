package fi.basse.tikape.kysymyspankki.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  
  private String addr;
  
  public Database() {
    this.addr = System.getenv("JDBC_DATABASE_URL");
  }
  
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(this.addr);
  }
}
