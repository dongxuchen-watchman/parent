import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by cdx on 2019/6/6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:activiti-context.xml")
public class getBpmnXmlByProcessdefinitionIdTest {

    @Autowired
    RepositoryService repositoryService ;

    @Test
    public void getBpmnXmlTest(){
            String processDefinitionKey = "testUploadAfterDatabaseExport";
            List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
//            for (ProcessDefinition processDefinition : processDefinitionList){
//                System.out.println(processDefinition.getKey());
//                System.out.println(processDefinition.getName());
//                System.out.println(processDefinition.getId());
//                System.out.println(processDefinition.getDeploymentId());
//            }
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
            String resourceName = processDefinition.getResourceName();
            System.out.println(resourceName);
            System.out.println(processDefinition.getDeploymentId());
//            repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName)
        System.out.println("模型查询");
        List<Model> modelList = repositoryService.createModelQuery().list();
        for(Model model : modelList){
            System.out.println(model.getId());
            System.out.println(model.getName());
            System.out.println(model.getKey());
            System.out.println(model.getCategory());
            System.out.println(model.getMetaInfo());
            System.out.println("================");
        }






    }

}
