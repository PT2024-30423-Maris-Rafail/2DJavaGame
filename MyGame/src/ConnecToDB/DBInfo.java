package ConnecToDB;

public class DBInfo {
    private static final String url = "jdbc:postgresql://localhost:5432/tilecollision";
    private static final String user = "postgres";
    private static final String password = "";//TODO input password here after creating the database

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

}
