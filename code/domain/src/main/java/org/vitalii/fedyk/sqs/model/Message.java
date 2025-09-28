package org.vitalii.fedyk.sqs.model;

import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Message {
  private String id;
  private String body;
  private Map<String, String> attributes;
  private String receiptHandle;
  private Instant timestamp;

  public Message(String body) {
    this(null, body, Map.of(), null, Instant.now());
  }
}
