package RegisterForm;

import ConnecToDB.ConnectR;
import GameState.UserLoginStatus;

public class LogInRegister {

    public static UserLoginStatus checkLogin(String userName, String password) {
        ConnectR connectR = new ConnectR();
        UserLoginStatus status;
        User user = connectR.getAccountBasedOnUsername(userName);
        User user1 = connectR.getAccountBasedOnEmail(userName);
        if (user == null && user1 == null) {
            status = UserLoginStatus.INVALID_PASS;
        } else {
            if (user != null) {
                if (!password.equals(user.getPassword())) {
                    status = UserLoginStatus.INVALID_PASS;
                }
                else status = UserLoginStatus.VALID_USERNAME;

            } else {//means user1 is not null
                if(!password.equals(user1.getPassword())) {
                    status = UserLoginStatus.INVALID_PASS;
                }
                else status = UserLoginStatus.VALID_EMAIL;
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
            user = connectR.getAccountBasedOnEmail(email);
            if (user != null) {
                status = UserLoginStatus.TAKEN_EMAIL;
            } else {
                if (!password.equals(confPassword)) {
                    status = UserLoginStatus.PASSWORD_MISMATCH;
                }
            }
        }
        return status;
    }
}
