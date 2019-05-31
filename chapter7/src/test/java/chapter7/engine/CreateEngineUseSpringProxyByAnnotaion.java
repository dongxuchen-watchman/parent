package chapter7.engine;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2019/5/30.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class CreateEngineUseSpringProxyByAnnotaion{

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ProcessEngineFactoryBean factoryBean;

    @Test
    public void testService() throws Exception{
        Assert.assertNotNull(runtimeService);


        ProcessEngine processEngine = factoryBean.getObject();
        Assert.assertNotNull(processEngine.getRuntimeService());


    }


}
