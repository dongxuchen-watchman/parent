package web.chapter6;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.util.json.HTTP;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import util.UserUtil;
import web.base.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/18.
 */
@Controller
@RequestMapping(value = "/chapter6")
public class TaskController extends AbstractController{

    private static final String TASK_LIST = "redirect:/chapter6/task/list";

    /**
     * 获取用户任务
     */
    @RequestMapping(value = "task/list")
    public ModelAndView todoTasks(HttpSession session){
        String viewName = "chapter6/task-list";
        User user = UserUtil.getUserFromSession(session);
        List<Task> taskList = taskService.createTaskQuery().taskCandidateOrAssigned(user.getId()).list();
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("tasks", taskList);
        return mav;
    }

    /**
     * 签收任务
     */
    @RequestMapping(value = "task/claim/{taskId}")
    public String claim(@PathVariable("taskId") String taskId , HttpSession session, RedirectAttributes redirectAttributes){
        String userId = UserUtil.getUserFromSession(session).getId();
        taskService.claim(taskId, userId);
        redirectAttributes.addFlashAttribute("message", "任务已签收");
        return TASK_LIST;
    }


    /**
     * 处理任务获取表单
     */
    @RequestMapping(value = "task/getform/{taskId}")
    public ModelAndView readTaskForm(@PathVariable("taskId") String taskId){
        ModelAndView mav = new ModelAndView("chapter6/task-form");
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        mav.addObject("taskFormData", taskFormData);
        return mav;
    }

    @RequestMapping(value = "task/complete/{taskId}")
    public String completeTask(@PathVariable("taskId") String taskId, HttpServletRequest request){
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        Map<String, String> formVaules = new HashMap<>();
        for (FormProperty formProperty : formProperties){
            if (formProperty.isWritable()){
                String value = request.getParameter(formProperty.getId());
                formVaules.put(formProperty.getId(), value);
            }
        }

        formService.submitTaskFormData(taskId, formVaules);
        return TASK_LIST;
    }




}
