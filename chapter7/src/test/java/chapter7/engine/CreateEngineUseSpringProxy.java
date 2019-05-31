package chapter7.engine;

import org.activiti.engine.RuntimeService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2019/5/30.
 */
public class CreateEngineUseSpringProxy {
    @Test
    public void createEngineUseSpring(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        ProcessEngineFactoryBean factoryBean = context.getBean(ProcessEngineFactoryBean.class);
        Assert.assertNotNull(factoryBean);
        RuntimeService bean = context.getBean(RuntimeService.class);
        Assert.assertNotNull(bean);




    }
}
