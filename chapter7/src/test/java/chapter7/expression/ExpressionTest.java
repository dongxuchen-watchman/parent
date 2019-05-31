package chapter7.expression;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
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

    @Test
    public void testExpression(){
        MyBean myBean = new MyBean();
        Map<String, Object> variables = new HashMap<>();
        variables.put("myBean", myBean);
        String name = "chen1";
        variables.put("name", name);

        System.out.println(repositoryService.createProcessDefinitionQuery().count());
        System.out.println(repositoryService.createDeploymentQuery().list());

        //运行期表达式
        identityService.setAuthenticatedUserId("chendongxu");
        String businesskey = "9999";
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("expression", businesskey, variables);
        System.out.println(processInstance.getId());
        Assert.assertEquals("chendongxu", runtimeService.getVariable(processInstance.getId(), "authenticatedUserIdForTest"));





    }


}
