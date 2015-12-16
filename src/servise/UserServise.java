package servise;

import entity.User;
import util.Main;

/**
 * Created by Administrator on 2015/12/11 0011.
 */
public class UserServise {
    public static boolean editUser(User u) {
        return u != null && Main.users.update(u);
    }
}
