package ru.external.kafka.camunda.consumer;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.model.bpmn.instance.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.external.kafka.camunda.delegate.GetFields;
import ru.external.kafka.camunda.model.Model;

import java.util.HashMap;
import java.util.Map;
import static org.camunda.spin.Spin.JSON;

@Service
public class ConsumerOutput {

    @Autowired
    RuntimeService runtimeService;

    public static final Logger logger = LoggerFactory.getLogger(ConsumerOutput.class);

    @KafkaListener(topics = "recruitment_app", groupId = "group_id")
    public void sendMessage(String message) {
        Model model = JSON(message).mapTo(Model.class);

        Map<String, Object> variables = new HashMap<>();
        variables.put("candidate_name", model.getCandidateName());
        variables.put("app_number", model.getAppNumber());
        variables.put("born_date", model.getBornDate());
        variables.put("experience", model.getExperience());
        variables.put("last_work_place", model.getLastWorkPlace());

        runtimeService.startProcessInstanceByKey("security", variables);

        logger.info("MESSAGE: " + message);
    }
}
