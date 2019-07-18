package web.chapter5.deployment;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import util.ActivitiUtils;
import web.base.AbstractController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

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
            //文件名扩展
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equalsIgnoreCase("zip") || extension.equalsIgnoreCase("bar")){
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deploymentBuilder.addZipInputStream(zip);
            }else {
                deploymentBuilder.addInputStream(fileName, fileInputStream);
            }

            deploymentBuilder.deploy();

        }catch (Exception e){
            logger.error("文件流有问题，部署失败");
        }

        return "redirect:process-list";
    }

    @RequestMapping(value = "/read-resource")
    public void readResource(@RequestParam("pdid") String prcessDefinitionId,
                             @RequestParam("resourceName") String resourceName, HttpServletResponse response){

        ProcessEngine processEngine = ActivitiUtils.getProcessEngine();
        ProcessDefinitionQuery pdq = processEngine.getRepositoryService().createProcessDefinitionQuery();
        ProcessDefinition pd = pdq.processDefinitionId(prcessDefinitionId).singleResult();
        //通过接口读取资源
        InputStream in = processEngine.getRepositoryService().getResourceAsStream(pd.getDeploymentId(), resourceName);

        byte[] b = new byte[1024];
        int len = -1;
        try {
            while(-1 != (len = in.read(b, 0, 1024))){
                response.getOutputStream().write(b ,0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value = "/delete-deployment")
    public String deleteProcessDefinition(@RequestParam("deploymentId") String deploymentId){
            ProcessEngine processEngine = ActivitiUtils.getProcessEngine();
            RepositoryService repositoryService = processEngine.getRepositoryService();
            repositoryService.deleteDeployment(deploymentId, true);
            return "redirect:process-list";
    }

}
