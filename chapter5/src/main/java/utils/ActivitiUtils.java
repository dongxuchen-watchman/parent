package utils;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

/**
 * Created by Administrator on 2019/6/18.
 */
public class ActivitiUtils {
    private static ProcessEngine processEngine;

    public static ProcessEngine getProcessEngine(){
        if (processEngine == null){
            synchronized (ActivitiUtils.class){
                if (processEngine == null) {
                    processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
                }
            }
        }
        return processEngine;
    }


}
