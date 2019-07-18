package util;

import org.activiti.engine.identity.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2019/7/18.
 */
public class UserUtil {
    public static final String USER = "user";

    /**
     *设置用户到session
     */
    public static void saveUserToSession(HttpSession session, User user){
        session.setAttribute(USER, user);
    }



    /**
     * 从session中获取用户信息
     */
    public static User getUserFromSession(HttpSession session){
        Object attribute = session.getAttribute(USER);
        return attribute == null ? null : (User)attribute;
    }



}
