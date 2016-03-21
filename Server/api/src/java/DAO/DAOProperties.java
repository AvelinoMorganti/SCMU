/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
public class DAOProperties {

    public int lastRegisterProperties() {

        String sql = "SELECT * FROM properties WHERE idproperties IN (SELECT MAX(idproperties) FROM properties)";
        int count = 0;
        try {

            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getUser
                    = conn.getConnection().prepareStatement(sql);
            ResultSet data = getUser.executeQuery();

            if (data.next() != false) {
                count = data.getInt(1);
                conn.closeConnection();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\n" + ex.getLocalizedMessage());
        }

        return count;
    }

    public Properties getPropertiesByID(long id) {

        Properties p = null;

        try {

            MySQLConnector conn = new MySQLConnector();
            conn.connect();

            PreparedStatement getUser
                    = conn.getConnection().prepareStatement("SELECT "
                            + "idproperties, "
                            + "lamp, "
                            + "alarm, "
                            + "smsNotifications, "
                            + "latitude, "
                            + "longitude, "
                            + "harmfulGases, "
                            + "luminosity "
                            + "FROM properties WHERE idproperties = ?");

            getUser.setLong(1, id);

            ResultSet data = getUser.executeQuery();

            long tmpId;
            boolean tmpLamp = false;
            boolean tmpAlarm = false;
            boolean tmpSMSNotifications = false;
            String tmpLatitude = "0.0";
            String tmpLongitude = "0.0";
            double harmfulGases = 0;
            double luminosity = 0;

            if (data == null) {
                return null;
            }

            if (data.next() != false) {
                tmpId = data.getLong(1);
                tmpLamp = (data.getInt(2) != 0);
                tmpAlarm = (data.getInt(3) != 0);
                tmpSMSNotifications = (data.getInt(4) != 0);
                tmpLatitude = data.getString(5);
                tmpLongitude = data.getString(6);
                harmfulGases = data.getDouble(7);
                luminosity = data.getDouble(8);

                conn.closeConnection();

                p = new Properties(id, tmpLamp, tmpAlarm, tmpSMSNotifications, tmpLatitude, tmpLongitude, harmfulGases, luminosity);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage() + "\n" + ex.getLocalizedMessage());
        }
        return p;
    }

    public boolean insertProperties(Properties properties) {
        if (properties != null) {
            try {
                MySQLConnector conn = new MySQLConnector();
                conn.connect();
                PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO properties VALUES(?,?,?,?,?,?,?,?)");

                insert.setInt(1, 0);
                //insert.setBoolean(2, "" + ((properties.isLamp()) ? 1 : 0));
                insert.setBoolean(2, properties.isLamp());
                insert.setBoolean(3, properties.isAlarm());
                insert.setBoolean(4, properties.isSms_notifications());
                insert.setString(5, properties.getLatitude());
                insert.setString(6, properties.getLongitude());
                insert.setDouble(7, properties.getHarmfulGases());
                insert.setDouble(8, properties.getLuminosity());
                insert.executeUpdate();

                conn.closeConnection();
                return true;

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(MySQLAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
