package org.vitalii.fedyk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.vitalii.fedyk.sqs.properties.AwsMainProperties;

@SpringBootApplication
@EnableConfigurationProperties(AwsMainProperties.class)
@EnableScheduling
public class BootApplication {

  public static void main(String[] args) {
    SpringApplication.run(BootApplication.class, args);
  }
}
