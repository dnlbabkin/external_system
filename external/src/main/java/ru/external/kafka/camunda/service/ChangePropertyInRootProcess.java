package ru.external.kafka.camunda.service;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

//TODO: Очень нужный класс, но ему не место в пакете service.
@Component
public class ChangePropertyInRootProcess {
    private final static Logger LOGGER = Logger.getLogger("Change property request");
    private String processName;

    @Autowired
    ApplicationContext applicationContext;

    public void execution(Expression property_val, Expression property_name, DelegateExecution delegateExecution) throws Exception {
        //Чтение основных данных
        String processId = delegateExecution.getProcessInstanceId();
        String buisnessKey = delegateExecution.getBusinessKey();
        processName = delegateExecution.getProcessEngineServices()
                .getRepositoryService()
                .getProcessDefinition(delegateExecution.getProcessDefinitionId())
                .getKey();
        ProcessEngine processEngine = delegateExecution.getProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        HistoryService historyService = processEngine.getHistoryService();
        //Поиск родительского процесса
        String rootProcessInstanceId = getSuperRootProcessID(delegateExecution);

        //Чтение и установка переменной - статуса
        Map<String, Object> variables = new HashMap<>();
        variables = delegateExecution.getVariables(); // <- больше нужно для отладки

        String property_name_str = (String) property_name.getValue(delegateExecution);
        String property_current_val = (String) runtimeService.getVariable(rootProcessInstanceId, property_name_str);
        String property_val_str = (String) property_val.getValue(delegateExecution);

        if(property_current_val == null) {property_current_val = "";}
        if(property_val_str == null) {property_val_str = "";}

        if(property_val_str.toLowerCase().equals(property_current_val.toLowerCase())){
            LOGGER.warning("New property equals old value");
        }
        else{
            runtimeService.setVariable(rootProcessInstanceId, property_name_str, property_val_str);
            runtimeService.setVariable(delegateExecution.getProcessInstanceId(), property_name_str, property_val_str);
            LOGGER.info("Property ["+property_name_str+"] changed to ["+property_val_str+"] successful in ["+processName+"]");
        }

    }

    public void execution(String property_val, String property_name, DelegateExecution delegateExecution) throws Exception {
        //Чтение основных данных
        String processId = delegateExecution.getProcessInstanceId();
        String buisnessKey = delegateExecution.getBusinessKey();
        processName = delegateExecution.getProcessEngineServices()
                .getRepositoryService()
                .getProcessDefinition(delegateExecution.getProcessDefinitionId())
                .getKey();
        ProcessEngine processEngine = delegateExecution.getProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        HistoryService historyService = processEngine.getHistoryService();
        //Поиск родительского процесса
        String rootProcessInstanceId = getSuperRootProcessID(delegateExecution);

        //Чтение и установка переменной - статуса
        Map<String, Object> variables = new HashMap<>();
        variables = delegateExecution.getVariables(); // <- больше нужно для отладки

        String property_current_val = (String) runtimeService.getVariable(rootProcessInstanceId, property_name);
        if(property_current_val == null) {property_current_val = "";}
        if(property_val == null) {property_val = "";}

        if(property_val.toLowerCase().equals(property_current_val.toLowerCase())){
            LOGGER.warning("New property equals old value");
        }
        else{
            runtimeService.setVariable(rootProcessInstanceId, property_name, property_val);
            runtimeService.setVariable(delegateExecution.getProcessInstanceId(), property_name, property_val);
            LOGGER.info("Property ["+property_name+"] changed to ["+property_val+"] successful in ["+processName+"]");
        }

    }

    private String getSuperRootProcessID(DelegateExecution delegateExecution) throws Exception{
        ProcessEngine processEngine = delegateExecution.getProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        String businessKey = delegateExecution.getBusinessKey();
        String superRootPocessID = "";

        try{
            String parent = delegateExecution.getParentId();
            if(parent == null){
                DelegateExecution superExecution = delegateExecution.getSuperExecution();
                if(superExecution == null){
                    superRootPocessID = delegateExecution.getProcessInstanceId();
                }
                else {
                    superRootPocessID = delegateExecution.getSuperExecution().getProcessInstanceId();
                }
            }
            else{
                List list_instances = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
                if(list_instances.size()==0){
                    superRootPocessID = delegateExecution.getProcessInstanceId();
                }else{
                    HistoricProcessInstance historicProcessInstance = (HistoricProcessInstance) list_instances.get(0);
                    superRootPocessID = historicProcessInstance.getId();
                }
            }

        }catch (Exception e){
            LOGGER.warning("Error in define parent process");
            throw new Exception("ChangePropertyInRootProcess: Ошибка при определении родительского процесса");
        }
        return superRootPocessID;
    }
}
