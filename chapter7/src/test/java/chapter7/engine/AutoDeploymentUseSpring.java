package chapter7.engine;

import org.activiti.engine.RepositoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2019/5/31.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-autodeployment.xml")
public class AutoDeploymentUseSpring {
    @Autowired
    public RepositoryService repositoryService;
    @Test
    public void testAutoDeployment(){
        //部署流程查询
//        long count = repositoryService.createDeploymentQuery().count();
        long count = repositoryService.createProcessDefinitionQuery().count();
        Assert.assertEquals(1, count);

    }



}
