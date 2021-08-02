package ru.external.kafka.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import ru.external.kafka.camunda.service.ChangePropertyInRootProcess;

import java.util.logging.Logger;

@Service("ChangeStatus")
public class ChangeStatus implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger("Change status");
    private Expression status_value;
    private Expression status_name;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ChangePropertyInRootProcess processChangeProperty = new ChangePropertyInRootProcess();
        processChangeProperty.execution(status_value, status_name, delegateExecution);
    }
}
