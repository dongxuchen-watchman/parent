package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2019/7/18.
 */
@Controller
@RequestMapping("/main")
public class MainController {
    @RequestMapping(value = "/index")
    public String index(){return "/main/index"; }



    @RequestMapping(value = "/welcome")
    public String welcome(){return  "/main/welcome";}


}
