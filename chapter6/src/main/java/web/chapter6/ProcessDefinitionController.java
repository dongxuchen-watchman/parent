package web.chapter6;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.identity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import util.UserUtil;
import web.base.AbstractController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/18.
 */
@Controller
@RequestMapping(value = "/chapter6")
public class ProcessDefinitionController extends AbstractController{

    @RequestMapping(value = "getform/start/{processDefinitionId}")
    public ModelAndView readStartForm(@PathVariable("processDefinitionId") String processDefinitionId) throws Exception {
        String viewName = "chapter6/start-process-form";
        ModelAndView modelAndView = new ModelAndView(viewName);
        StartFormData startFormData = formService.getStartFormData(processDefinitionId);
        modelAndView.addObject("startFormData", startFormData);
        modelAndView.addObject("processDefinitionId", processDefinitionId);
        return modelAndView;
    }

    @RequestMapping(value = "process-instance/start/{processDefinitionId}")
    public String startProcessInstance(@PathVariable("processDefinitionId") String processDefinitionId, HttpServletRequest request){
        StartFormData startFormData = formService.getStartFormData(processDefinitionId);
        List<FormProperty> formProperties = startFormData.getFormProperties();
        Map<String, String> formValues = new HashMap<>();
        for (FormProperty formProperty : formProperties){
            String value = request.getParameter(formProperty.getId());
            formValues.put(formProperty.getId(), value);
        }
        //获取当前用户
        User user = UserUtil.getUserFromSession(request.getSession());
        identityService.setAuthenticatedUserId(user.getId());
        //提交表单并启动一个新的流程实例
        formService.submitStartFormData(processDefinitionId, formValues);

        return "redirect:/chapter5/process-list";
    }


}
