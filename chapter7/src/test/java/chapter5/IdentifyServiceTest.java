package chapter5;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2019/6/6.
 */
public class IdentifyServiceTest {

    @Test
    public void testUser(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        //新增用户
        User user = identityService.newUser("chendongxu");
        user.setFirstName("chen");
        user.setLastName("dongxu");
        user.setEmail("chendongxu@eversec.cn");
        identityService.saveUser(user);
        //验证用户是否保存成功
        User userInDb = identityService.createUserQuery().userId("chendongxu").singleResult();
        Assert.assertNotNull(userInDb);

        identityService.deleteUser("chendongxu");

        userInDb = identityService.createUserQuery().userId("chendongxu").singleResult();

        Assert.assertNull(userInDb);

    }

    @Test
    public void groupTest(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        Group group = identityService.newGroup("deptLeader");
        group.setName("部门领导");
        group.setType("assignment");
        identityService.saveGroup(group);

        List<Group> groupList = identityService.createGroupQuery().groupId("deptLeader").list();
        Assert.assertEquals(1, groupList.size());

        identityService.deleteGroup("deptLeader");

        groupList = identityService.createGroupQuery().groupId("deptLeader").list();
        Assert.assertEquals(0, groupList.size());


    }

    @Test
    public void testUserAndGroupMemership(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        //新增用户
        User user = identityService.newUser("chendongxu");
        user.setFirstName("chen");
        user.setLastName("dongxu");
        user.setEmail("chendongxu@eversec.cn");
        identityService.saveUser(user);
        //验证用户是否保存成功
        User userInDb = identityService.createUserQuery().userId("chendongxu").singleResult();
        Assert.assertNotNull(userInDb);


        Group group = identityService.newGroup("deptLeader");
        group.setName("部门领导");
        group.setType("assignment");
        identityService.saveGroup(group);
        identityService.createMembership("chendongxu", "deptLeader");

        User userInGroup = identityService.createUserQuery().memberOfGroup("deptLeader").singleResult();
        Assert.assertNotNull(userInGroup);

        Assert.assertEquals("chendongxu", userInGroup.getId());

        Group groupContainsCDX = identityService.createGroupQuery().groupMember("chendongxu").singleResult();

        Assert.assertNotNull(groupContainsCDX);
        Assert.assertEquals("deptLeader", groupContainsCDX.getId());









    }



}
