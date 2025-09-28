package org.vitalii.fedyk.sqs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserRegistrationData {
  private String firstName;
  private String lastName;
  private String email;
  private String locale;
}
