package chapter7.engine;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2019/5/30.
 */
public class ProcessEngineConfigurationTest {
    @Test
    public void userDefaultName(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();
        System.out.println(processEngine.hashCode());
        Assert.assertEquals("default", processEngine.getName());


    }


}
