package chapter7.expression;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-autodeployment.xml")
public class ExpressionTest {

    @Autowired
    ProcessEngine processEngine;
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    IdentityService identityService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    @Test
    public void testExpression(){
        MyBean myBean = new MyBean();
        Map<String, Object> variables = new HashMap<>();
        variables.put("myBean", myBean);
        String name = "chen1";
        variables.put("name", name);

        System.out.println(repositoryService.createProcessDefinitionQuery().list());
        System.out.println(repositoryService.createDeploymentQuery().list());

        //运行期表达式
        identityService.setAuthenticatedUserId("chendongxu");
        String businesskey = "9999";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("expression", businesskey, variables);
        System.out.println(processInstance.getId());
        Assert.assertEquals("chendongxu", runtimeService.getVariable(processInstance.getId(), "authenticatedUserIdForTest"));

        Assert.assertEquals("chen1, add by print(String name)", runtimeService.getVariable(processInstance.getId(), "returnValue"));

        Assert.assertEquals(businesskey, runtimeService.getVariable(processInstance.getId(), "businessKey"));

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        String setByTask = (String)taskService.getVariable(task.getId(), "setByTask");
        Assert.assertEquals("I am setted by DelegateTask, " + name, setByTask);







    }


}
