import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).singleResult();
            String resourceName = processDefinition.getResourceName();
            System.out.println(resourceName);

    }

}
