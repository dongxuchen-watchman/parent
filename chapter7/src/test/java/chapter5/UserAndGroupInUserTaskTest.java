package chapter5;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2019/6/6.
 */
public class UserAndGroupInUserTaskTest extends AbstractTest{

    @Before
    public void setUp() throws Exception {
        super.setUp();

        Group group = identityService.newGroup("deptLeader");
        group.setName("部门领导");
        group.setType("assignment");
        identityService.saveGroup(group);

        User user = identityService.newUser("chendongxu");
        user.setFirstName("chen");
        user.setLastName("dongxu");
        user.setEmail("chendongxu@eversec.cn");
        identityService.saveUser(user);

        identityService.createMembership("chendongxu", "deptLeader");
    }

    @After
    public void afterInvokeTestMethod(){
        identityService.deleteMembership("chendongxu", "deptLeader");
        identityService.deleteGroup("deptLeader");
        identityService.deleteUser("chendongxu");
    }

    @Test
    @Deployment(resources = ("bpmn/userAndGroupInUserTask.bpmn"))
    public void testUserAndGroupInUserTask(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process1");
        System.out.println(processInstance.getProcessDefinitionKey());
        System.out.println(processInstance.getProcessDefinitionId());
        Assert.assertNotNull(processInstance);

        //根据角色查找任务
        Task task = taskService.createTaskQuery().taskCandidateOrAssigned("chendongxu").singleResult();

        taskService.claim(task.getId(), "chendongxu");
        taskService.complete(task.getId());




    }


}
