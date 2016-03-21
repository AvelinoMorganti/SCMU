/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import classes.Account;
import classes.MySQLAuthenticator;
import classes.Properties;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author avelino
 */
public class DAOAccount {

    public Account getAccount(String name) {

        Account account = null;

        try {

            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getUser
                    = conn.getConnection().prepareStatement("SELECT password, salt, locked "
                            + "FROM account "
                            + "WHERE username = ?");

            getUser.setString(1, name);

            ResultSet data = getUser.executeQuery();

            String tmpPass = "";
            String tmpSalt = "";
            boolean tmp = false;

            if (data == null) {
                return null;
            }

            if (data.next() != false) {
                tmpPass = data.getString(1);
                tmpSalt = data.getString(2);
                tmp = (data.getInt(3) != 0);

                conn.closeConnection();

                account = new Account(name, tmpPass, tmpSalt, tmp);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\n" + ex.getLocalizedMessage());
        }
        return account;
    }

    public boolean createAccount(Account account, Properties properties, long id) {
        if (account != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();
                PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO account VALUES(?,?,?,?,?,?)");

                insert.setInt(1, 0);
                insert.setString(2, account.getUsername());
                insert.setString(3, account.getPassword());
                insert.setString(4, account.getSalt());
                insert.setString(5, "0");
                
                
                insert.setLong(6, properties.getId());
                //JOptionPane.showMessageDialog(null, "testeeeee" + properties.getId());
                insert.executeUpdate();

                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro:" + ex.getLocalizedMessage() + "\n\n" + ex.getMessage());
                Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
