package org.vitalii.fedyk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {
  @Bean
  @ConfigurationProperties(prefix = "aws.sqs")
  public AwsMainProperties sqsProperties() {
    return new AwsMainProperties();
  }
}
