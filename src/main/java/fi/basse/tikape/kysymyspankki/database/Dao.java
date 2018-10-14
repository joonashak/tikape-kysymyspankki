package fi.basse.tikape.kysymyspankki.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao {
  
  private Database db;
  private Connection conn;

  public Dao(Database db) {
    this.db = db;
  }
  
  public Connection getConnection() throws SQLException {
    this.conn = db.getConnection();
    return this.conn;
  }

  public Database getDb() {
    return this.db;
  }
  
  public final void close(ResultSet rs, PreparedStatement stmt)
          throws SQLException {
    rs.close();
    stmt.close();
    this.conn.close();
  }
}
