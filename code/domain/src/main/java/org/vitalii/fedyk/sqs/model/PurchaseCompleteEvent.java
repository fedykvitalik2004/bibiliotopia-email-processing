package org.vitalii.fedyk.sqs.model;

import java.util.List;
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
public class PurchaseCompleteEvent {
  private String firstName;
  private String lastName;
  private List<BookData> books;
  private String email;
  private String currency;
  private String locale;
}
