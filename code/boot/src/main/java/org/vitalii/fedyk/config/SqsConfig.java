package org.vitalii.fedyk.config;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.Executors;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vitalii.fedyk.sqs.properties.AwsMainProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.batchmanager.SqsAsyncBatchManager;
import software.amazon.awssdk.services.sqs.model.MessageSystemAttributeName;

/**
 * Spring configuration for AWS SQS. Provides beans for asynchronous SQS client and batch manager
 * configured with application AWS credentials and SQS settings.
 */
@Configuration
@AllArgsConstructor
public class SqsConfig {
  private AwsMainProperties sqsProperties;

  /**
   * Creates an asynchronous SQS client using the configured AWS region and credentials.
   *
   * @return a configured {@link SqsAsyncClient} instance
   */
  @Bean
  public SqsAsyncClient sqsClient() {
    return SqsAsyncClient.builder()
        .region(Region.of(this.sqsProperties.getRegion()))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                    this.sqsProperties.getAccessKey(), this.sqsProperties.getSecretKey())))
        .build();
  }

  /**
   * Creates an asynchronous SQS batch manager for processing messages in batches. Configures thread
   * pool, batch size, message attributes, visibility timeout, and minimum wait duration for safety.
   *
   * @param sqsAsyncClient the SQS client to be used by the batch manager
   * @return a configured {@link SqsAsyncBatchManager} instance
   */
  @Bean
  public SqsAsyncBatchManager sqsAsyncBatchManager(final SqsAsyncClient sqsAsyncClient) {
    return SqsAsyncBatchManager.builder()
        .client(sqsAsyncClient)
        .scheduledExecutor(Executors.newScheduledThreadPool(4))
        .overrideConfiguration(
            b ->
                b.receiveMessageMinWaitDuration(Duration.ofSeconds(10)) // safety wait time
                    .receiveMessageVisibilityTimeout(Duration.ofSeconds(30)) // message lock
                    .receiveMessageAttributeNames(Collections.singletonList("*")) // all attributes
                    .maxBatchSize(10) // max messages per batch
                    .receiveMessageSystemAttributeNames(
                        Collections.singletonList(
                            MessageSystemAttributeName.ALL))) // all system attributes
        .build();
  }
}
