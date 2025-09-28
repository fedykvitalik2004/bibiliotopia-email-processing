package org.vitalii.fedyk.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@AllArgsConstructor
public class SqsConfig {
  private AwsMainProperties sqsProperties;

  @Bean
  public SqsClient sqsClient() {
    return SqsClient.builder()
        .region(Region.of(sqsProperties.getRegion()))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                    sqsProperties.getAccessKey(), sqsProperties.getSecretKey())))
        .build();
  }
}
