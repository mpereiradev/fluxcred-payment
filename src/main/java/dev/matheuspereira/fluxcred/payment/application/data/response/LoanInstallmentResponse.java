package dev.matheuspereira.fluxcred.payment.application.data.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanInstallmentResponse {
  @Schema(description = "Unique identifier of the installment", example = "101")
  private Integer id;

  @Schema(description = "The identifier of loan the installment", example = "10")
  private Integer loanId;

  @Schema(description = "Sequential number of the installment", example = "1")
  private int installmentNumber;

  @Schema(description = "Amount due for this installment", example = "350.50")
  private BigDecimal amount;

  @Schema(description = "Date when the installment is due", example = "2024-12-15")
  private LocalDate dueDate;

  @Schema(
      description = "Actual payment date of the installment, if paid",
      example = "2024-12-16T14:30:00")
  private LocalDateTime paymentDate;
}
