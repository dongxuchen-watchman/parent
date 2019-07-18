package com.wanmei.activiti.chapter6;

import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/16.
 */
public class LeaveDynamicFormTest extends AbstractTest{

    @Test
    @Deployment(resources = "bpmn/leave.bpmn")
    public void testFormType(){
        //验证是否部署成功
        long count = repositoryService.createProcessDefinitionQuery().count();
        System.out.println(repositoryService.createProcessDefinitionQuery().singleResult().getKey());
        Assert.assertEquals(1, count);
        //启动流程的人
        String currentUserId = "chendongxu";
        identityService.setAuthenticatedUserId(currentUserId);
        //获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("leave").singleResult();

        //创建表单
        Map<String, String> vars = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        String startDate = sdf.format(ca.getTime());
        ca.add(Calendar.DAY_OF_MONTH, 2);
        String endDate = sdf.format(ca.getTime());
        vars.put("startDate", startDate);
        vars.put("endDate", endDate);
        vars.put("reason", "公休");

        //通过表单对象启动流程实例

        ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), vars);

        Assert.assertNotNull(processInstance);
        //部门领导通过审批 Candidate候选组
        Task deptLeaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
        vars = new HashMap<>();
        vars.put("deptLeaderApproved", "true");
        formService.submitTaskFormData(deptLeaderTask.getId(), vars);
        //人事通过审批
        Task hrTask = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
        vars = new HashMap<>();
        vars.put("hrApproved", "true");
        formService.submitTaskFormData(hrTask.getId(), vars);
        //销假
        Task reportBackTask = taskService.createTaskQuery().taskAssignee(currentUserId).singleResult();
        vars = new HashMap<>();
        vars.put("reportBackDate", sdf.format(ca.getTime()));
        formService.submitTaskFormData(reportBackTask.getId(), vars);
        //查看流程是否结束

        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .finished().singleResult();
        Assert.assertNotNull(historicProcessInstance);
        //读取历史变量
        Map<String, Object> historyVar = packageVariables(processInstance);

        //验证执行结果
        Assert.assertEquals("ok", historyVar.get("result"));






















    }

    public Map<String, Object> packageVariables(ProcessInstance processInstance){
        Map<String, Object> historyVar = new HashMap<>();
        List<HistoricDetail> list = historyService.createHistoricDetailQuery().processInstanceId(processInstance.getId()).list();
        for (HistoricDetail detail : list){
            if (detail instanceof HistoricFormProperty){
                HistoricFormProperty historicFormProperty = (HistoricFormProperty) detail;
                historyVar.put(historicFormProperty.getPropertyId(), historicFormProperty.getPropertyValue());
                System.out.println("form field: taskid = " + historicFormProperty.getTaskId()
                        + ", " + historicFormProperty.getPropertyId() + "=" + historicFormProperty.getPropertyValue());
            } else if (detail instanceof HistoricVariableUpdate){
                HistoricVariableUpdate historicVariableUpdate = (HistoricVariableUpdate) detail;
                historyVar.put(historicVariableUpdate.getVariableName() , historicVariableUpdate.getValue());
                System.out.println("var : " + historicVariableUpdate.getVariableName()  + "=" + historicVariableUpdate.getValue());
            }
        }




        return  historyVar;
    }

}
