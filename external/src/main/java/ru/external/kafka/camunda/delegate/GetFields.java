package ru.external.kafka.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.external.kafka.camunda.model.Model;

@Service("GetFields")
public class GetFields implements JavaDelegate {

    @Autowired
    private KafkaTemplate<String, Model> kafkaTemplate;
    private Model model = new Model();
    private static final String TOPIC = "recruitment_app";

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
//        delegateExecution.setVariable("candidate_name", model.getCandidateName().toString());
//        delegateExecution.setVariable("app_number", model.getAppNumber().toString());
//        delegateExecution.setVariable("born_date", model.getBornDate().toString());
//        delegateExecution.setVariable("experience", model.getExperience().toString());
//        delegateExecution.setVariable("last_work_place", model.getLastWorkPlace().toString());

        //kafkaTemplate.send(TOPIC, model);
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
