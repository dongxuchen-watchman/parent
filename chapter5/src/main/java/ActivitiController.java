import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/6/6.
 */
@Controller
@RequestMapping(value = "/chapter5")
public class ActivitiController {
    //流程定义列表
    @RequestMapping(value = "/process-list")
    public ModelAndView processList(){
        ModelAndView mav = new ModelAndView("workflow/process-list");
        List<Object> objects = new ArrayList<>();
        mav.addObject("objects", objects);
        return mav;
    }

    //部署全部流程

    @RequestMapping(value = "redeploy/all")
    public String redeployAll(){

        return null;

    }



}
