package org.vitalii.fedyk.email.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.email.model.PurchaseEmailContext;
import org.vitalii.fedyk.sqs.model.BookData;
import org.vitalii.fedyk.sqs.model.PurchaseCompleteEvent;

class PurchaseEmailContextMapperTest {

  private final PurchaseEmailContextMapper mapper = new PurchaseEmailContextMapper();

  @Test
  void shouldMapAndFormatCorrectly() {
    // Given
    final var book1 = new BookData("The Witcher", "Sapkowski", new BigDecimal("100.00"), 2);
    final var book2 = new BookData("Clean Code", "Uncle Bob", new BigDecimal("250.559"), 1);

    final var event =
        PurchaseCompleteEvent.builder()
            .firstName("John")
            .lastName("Green")
            .books(List.of(book1, book2))
            .currency("UAH")
            .locale("en-UA")
            .build();

    final var expected =
        PurchaseEmailContext.builder()
            .firstName("John")
            .lastName("Green")
            .books(
                List.of(
                    PurchaseEmailContext.EmailLineItem.builder()
                        .title("The Witcher")
                        .author("Sapkowski")
                        .price("UAH100.00")
                        .quantity(2)
                        .lineItemTotal("UAH200.00")
                        .build(),
                    PurchaseEmailContext.EmailLineItem.builder()
                        .title("Clean Code")
                        .author("Uncle Bob")
                        .price("UAH250.56")
                        .quantity(1)
                        .lineItemTotal("UAH250.56")
                        .build()))
            .totalOrderPrice("UAH450.56")
            .build();

    // When
    final PurchaseEmailContext actual = this.mapper.toEmailContext(event);

    // Then
    assertEquals(expected, actual);
  }
}
