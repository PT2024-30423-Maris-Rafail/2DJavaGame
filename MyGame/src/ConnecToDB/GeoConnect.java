package ConnecToDB;

import geoGame.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class GeoConnect {
    ConnectR connectR;
    public GeoConnect(ConnectR connectR) {
        this.connectR = connectR;
    }
    /**
     * @param id id of certain coordinate set in the database
     * @return object of class Location with necessary coordinates as string and country name
     */
    public Location getCoordinates(int id) {
        Location location;
        try {
            Statement stmt = connectR.connection.createStatement();

            String query = "SELECT locations.coordinates,country.country_name from locations JOIN public.country ON locations.country_id = country.id WHERE locations.id = " + id + ";";
            //System.out.println(random.nextInt(tableSize));
            ResultSet rs = stmt.executeQuery(query);
            //System.out.println("negro");
            if (rs.next()) {
                String coordinates = rs.getString("coordinates");
                //System.out.println(coordinates);
                String countryName = rs.getString("country_name");
                //.println(countryName);
                location = new Location();
                location.setCoordinates(coordinates);
                location.setCountry(countryName);
                return location;
            }
            //System.out.println("cioara");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Allow for unique location selection per each game round + reset
    public void markVisited(int id) {
        try {
            Statement st = connectR.connection.createStatement();
            String query = "UPDATE locations SET checked = true WHERE id = " + id + ";";
            st.executeUpdate(query);
            //ResultSet rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("Marked id " + id + " visited");
    }

    public void markUNVisited(int id) {
        try {
            Statement st = connectR.connection.createStatement();
            String query = "UPDATE locations SET checked = false WHERE id = " + id + ";";
            st.executeUpdate(query);
            //ResultSet rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkVisited(int id) {
        try {
            Statement st = connectR.connection.createStatement();
            String query = "SELECT locations.checked FROM locations WHERE id = " + id + ";";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return rs.getBoolean("checked");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getValidIndex(int size) {
        Random random = new Random();
        int niggus = random.nextInt(size) + 1;

        //System.out.println("NIGGUS ESTE " + niggus);
        //niggus = 5;
        int copy = niggus + 1;
        while (niggus > 0 && checkVisited(niggus)) {
            //System.out.println("FOR NIGGUS = " + niggus + " IS " + checkVisited(niggus));
            niggus--;
        }
        if (niggus == 0) {
            while (checkVisited(copy)) {
                copy++;
            }
            niggus = copy;
        }

        markVisited(niggus);
        return niggus;
    }

    public void resetTable(int size) {
        for (int i = 1; i <= size; i++) {
            if (checkVisited(i)) {
                markUNVisited(i);
            }

        }
    }
}
