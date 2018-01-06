package service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;

public class DBManager {
 
    Connection conn = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;

    public DBManager() {
        try {

            //STEP 2: Register JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "kormanak4", "monika");
            System.out.println("Connected database successfully...");
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public ResultSet querySQL(String query) {
        try {
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            //stmt.setMaxRows(300); //strankovanie po 300 rows
            //stmt.setFetchDirection(); toutou metodou sa asi bude robit seek
            return stmt.executeQuery(query);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertWithBlobSql(String query, byte[] img) {
        boolean result = true;
        try {
            //STEP 4: Execute a query
            pstmt = conn.prepareStatement(query);
            InputStream fis = new ByteArrayInputStream(img);

            pstmt.setBinaryStream(1, fis, img.length);
            pstmt.executeUpdate();

            //return stmt.executeQuery(query);
        } catch (SQLException se) {
            result = false;
            se.printStackTrace();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean insertSql(String query) {
        boolean result = true;
        try {
            //STEP 4: Execute a query
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();

            //return stmt.executeQuery(query);
        } catch (SQLException se) {
            result = false;
            se.printStackTrace();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateSql(String query) {
        boolean result = true;
        try {
            //STEP 4: Execute a query
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();

            //return stmt.executeQuery(query);
        } catch (SQLException se) {
            result = false;
           se.printStackTrace();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
   }
}//end JDBCExample
