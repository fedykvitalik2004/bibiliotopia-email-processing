package org.vitalii.fedyk.email.model;

import java.util.List;
import lombok.Builder;

@Builder
public record PurchaseEmailContext(
    String firstName, String lastName, List<EmailLineItem> books, String totalOrderPrice) {
  @Builder
  public record EmailLineItem(
      String title, String author, String price, int quantity, String lineItemTotal) {}
}
