package org.vitalii.fedyk.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwsMainProperties {
  private String region;

  private String accessKey;

  private String secretKey;
}
