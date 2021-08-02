package ru.external.kafka.camunda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.external.kafka.camunda.consumer.ConsumerOutput;
import ru.external.kafka.camunda.model.Model;

import static org.camunda.spin.Spin.JSON;

@SpringBootApplication
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);

    Model model = new Model("Your Mom Gay", 12, "14 Faggot 1488", 2, "Town of niggers");

    String json = JSON(model).toString();

    System.out.println(json);
  }

}