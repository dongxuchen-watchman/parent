package web.identity;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import util.UserUtil;
import web.base.AbstractController;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by cdx on 2019/7/18.
 */
@Controller
@RequestMapping(value = "/user")
public class UseController extends AbstractController{
    private static Logger logger = LoggerFactory.getLogger(UseController.class);

    private IdentityService identityService = processEngine.getIdentityService();

    /**
     * 登录系统
     */
    @RequestMapping(value = "/logon")
    public String logon(@RequestParam("username") String userName, @RequestParam("password") String password, HttpSession session){
        logger.debug("logon request: {username = {}, password = {}}" , userName, password);
        boolean checkPassword = identityService.checkPassword(userName, password);
        if (checkPassword){
            //查看用户是否存在
            User user = identityService.createUserQuery().userId(userName).singleResult();
            UserUtil.saveUserToSession(session, user);
            //读取角色
            List<Group> groupList = identityService.createGroupQuery().groupMember(user.getId()).list();
            session.setAttribute("groups", groupList);

            String[] groupNames = new String[groupList.size()];
            for (int i = 0; i < groupNames.length; i++){
                groupNames[i] = groupList.get(i).getName();
            }
            session.setAttribute("groupNames", ArrayUtils.toString(groupNames));
            return "redirect:/main/index";

        } else {
            return "redirect:/login.jsp?error=true";
        }


    }



    /**
     * 退出登录
     */




}
