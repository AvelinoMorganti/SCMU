package DAO;

import classes.Account;
import classes.MySQLAuthenticator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DAOUsers {

    public DAOUsers() {
    }

    public ArrayList<Account> getAllAccounts() {

        ArrayList<Account> allAccounts = new ArrayList<Account>();

        try {
            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            String sql = "SELECT * FROM users";
            conn.getStatement().execute(sql);
            ResultSet data = conn.getStatement().getResultSet();

            if (data == null) {
                return null;
            }

            while (data.next()) {
                String username = data.getString("username");
                String password = data.getString("password");
                String salt = data.getString("salt");
                boolean tmp = (data.getInt("locked") != 0);

                allAccounts.add(new Account(username, password, salt, tmp));

            }
            conn.closeConnection();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\n" + ex.getLocalizedMessage());

        }
        return allAccounts;
    }

    public boolean lockAccount(String username, String locked) {
        if (username != null && username != "") {

            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();

                PreparedStatement update
                        = conn.getConnection().prepareStatement("UPDATE users "
                                + "SET locked = ?"
                                + "WHERE username = ?");

                update.setString(1, locked);
                update.setString(2, username);
                update.executeUpdate();
                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
