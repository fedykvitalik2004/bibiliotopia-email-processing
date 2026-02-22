package org.vitalii.fedyk.sqs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for AWS access. Holds the AWS region, access key, and secret key
 * required for connecting to AWS services.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "aws.sqs")
public class AwsMainProperties {
  private String region;

  private String accessKey;

  private String secretKey;

  private String queueUrl;
}
