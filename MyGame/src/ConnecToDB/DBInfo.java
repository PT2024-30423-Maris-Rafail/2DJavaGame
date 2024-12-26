package ConnecToDB;

public class DBInfo {
    private static final String url = "jdbc:postgresql://localhost:5432/tilecollision";
    private static final String user = "postgres";
    private static final String password = "Animeotaku12*";
    private static final String adminUser = "admin";
    private static final String adminPassword = "admin";
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
