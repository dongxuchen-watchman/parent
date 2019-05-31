package chapter7.expression;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/5/31.
 */
public class MyBean implements Serializable{

    public void print(){
        System.out.println("print content by print()");
    }

    public String print(String name){
        System.out.println("print content by print(String name), value is :" + name);
        return name += ", add by print(String name)";
    }

    public void invokeTask(DelegateTask task){
        task.setVariable("setByTask", "I am setted by DelegateTask, " + task.getVariable("name"));
    }

    public String printBkey(DelegateExecution execution){
        String processBusinessKey = execution.getProcessBusinessKey();
        System.out.println("process instance id: " + execution.getProcessInstanceId() + ", business key: " + processBusinessKey);
        return  processBusinessKey;

    }

}
