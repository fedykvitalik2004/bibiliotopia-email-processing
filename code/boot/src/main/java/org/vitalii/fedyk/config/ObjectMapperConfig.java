package org.vitalii.fedyk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for Jackson's ObjectMapper. Provides a customized ObjectMapper bean for JSON
 * serialization and deserialization. Configured to handle Java 8 date/time types and to serialize
 * dates as ISO-8601 strings instead of timestamps.
 */
@Configuration
public class ObjectMapperConfig {
  /**
   * Creates and configures an ObjectMapper bean. - Registers JavaTimeModule to support Java 8
   * date/time API. - Disables writing dates as timestamps to produce ISO-8601 date strings.
   *
   * @return the configured ObjectMapper instance
   */
  @Bean
  public ObjectMapper objectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }
}
