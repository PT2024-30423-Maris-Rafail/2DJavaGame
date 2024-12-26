package RegisterForm;

import ConnecToDB.ConnectR;
import GameState.UserLoginStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInRegister {
    public static String emailPattern = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    public static Pattern pattern = Pattern.compile(emailPattern);

    public static UserLoginStatus checkLogin(String userName, String password) {
        ConnectR connectR = new ConnectR();
        UserLoginStatus status;
        User user = connectR.getAccountBasedOnUsername(userName);
        User user1 = connectR.getAccountBasedOnEmail(userName);
        if (user == null && user1 == null) {
            status = UserLoginStatus.INVALID_NAME;
        } else {
            if (user != null) {
                if (!password.equals(user.getPassword())) {
                    status = UserLoginStatus.INVALID_PASS;
                } else status = UserLoginStatus.VALID_USERNAME;

            } else {//means user1 is not null
                if (!password.equals(user1.getPassword())) {
                    status = UserLoginStatus.INVALID_PASS;
                } else status = UserLoginStatus.VALID_EMAIL;
            }
        }
        return status;
    }

    public static UserLoginStatus registerUser(String userName, String password, String confPassword, String email) {
        ConnectR connectR = new ConnectR();
        UserLoginStatus status = UserLoginStatus.VALID;
        User user = connectR.getAccountBasedOnUsername(userName);
        if (user != null) {
            status = UserLoginStatus.TAKEN_NAME;
        } else {
            Matcher matcher = pattern.matcher(email);
            if(!matcher.matches()) {
                status = UserLoginStatus.INVALID_EMAIL;
            }
            else{
                user = connectR.getAccountBasedOnEmail(email);
                if (user != null) {
                    status = UserLoginStatus.TAKEN_EMAIL;
                } else {
                    if (!password.equals(confPassword)) {
                        status = UserLoginStatus.PASSWORD_MISMATCH;
                    }
                }
            }

        }
        return status;
    }
    public static boolean checksAdmin(String userName) {
        return userName.equals("admin");
    }
}
