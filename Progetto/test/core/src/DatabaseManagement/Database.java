package DatabaseManagement;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

/*
    You must have jdbc library in the same package of desktop launcher!
 */

public class Database {

    public Database() {
    }

    private boolean check () {
        boolean empity = true;
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            //I use this query and not "SELECT COUNT..." because the last method returns always a result set, while
            //now if there aren't tuple rs.next() is false!
            String query = "SELECT * FROM PLAYERS";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                empity = false;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
        return empity;
    }

    public String start () {
        String s = "";
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            //!!!!!!!!!! jdbc:sqlite:path of db!!!!!!!!!!
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            System.out.println("Connection established!\nResult set:");
            if (check()) {
                System.out.println("Database empity!");
                return s;
            }
            String query = "SELECT * FROM GAMES ORDER BY NAME";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //rs.getString() take as parameter the name or the numeric index of the column
                System.out.println("- " + rs.getString("ID") + " | " + rs.getString("NICKNAME") + " | " + rs.getString("POINTS"));
                s += "- " + rs.getString("ID") + " | " + rs.getString("NICKNAME") + " | " + rs.getString("POINTS");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            s = "There is a big problem!";
            return s;
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
            s = "There is a huge problem!";
            return s;
        }
        return s;
    }

    public void insert (String id, String nome, int punteggio) {
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            //System.out.println("Connection established!");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO GAMES VALUES (?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, nome);
            stmt.setInt(3, punteggio);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
    }

    public void drop (String name) {
        BufferedReader fr = new BufferedReader(new InputStreamReader(System.in));
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            String a = fr.readLine();
            String query = "DELETE FROM PLAYERS WHERE NAME = '" + name + "'";
            stmt.executeUpdate(query);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void empity () {
        try {
            String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            String url = "jdbc:sqlite:DB.sqlite";
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            //System.out.println("Connection established!");
            String query = "DELETE FROM PLAYERS";
            stmt.executeUpdate(query);
            System.out.println("Database empity!");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.err.println(cnfe.getMessage());
        }
    }
}
