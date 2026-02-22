package org.vitalii.fedyk.sqs.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookData {
  private String title;
  private String author;
  private BigDecimal price;
  private Integer quantity;
}
