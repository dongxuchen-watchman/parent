package web.deployment;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import utils.ActivitiUtils;
import web.base.AbstractController;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2019/6/18.
 */
@Controller
@RequestMapping(value = "/chapter5")
public class DeploymentController extends AbstractController {

    @RequestMapping(value = "/process-list")
    public ModelAndView processList(){
        ProcessEngine processEngine = ActivitiUtils.getProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ModelAndView mav = new ModelAndView("chapter5/process-list");

        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();


        mav.addObject("processDefinitionList", processDefinitionList);
        return mav;


    }

    @RequestMapping(value = "deploy")
    public String deploy(@RequestParam(value = "file")MultipartFile file){
        String fileName = file.getOriginalFilename();
        try{
            InputStream fileInputStream = file.getInputStream();
            ProcessEngine processEngine = ActivitiUtils.getProcessEngine();
            RepositoryService repositoryService = processEngine.getRepositoryService();
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();





        }catch (Exception e){
            logger.error("文件流有问题，部署失败");
        }




        return "redirect:process-list";
    }

}
