package web.base;

import org.activiti.engine.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ActivitiUtils;

/**
 * Created by Administrator on 2019/6/18.
 */
public abstract class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(AbstractController.class);
    protected ProcessEngine processEngine = null;
    public AbstractController(){
        super();
        processEngine = ActivitiUtils.getProcessEngine();
    }


}
