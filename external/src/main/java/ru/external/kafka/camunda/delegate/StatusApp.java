package ru.external.kafka.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("StatusApp")
public class StatusApp implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        WebClient webClient = WebClient.create();
        webClient.post().uri(String.format("http://localhost:9090/status/%s/%s/", delegateExecution.getVariable("app_number"), delegateExecution.getVariable("app"))).header(HttpHeaders.ACCEPT).accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(String.class).block();
    }
}
