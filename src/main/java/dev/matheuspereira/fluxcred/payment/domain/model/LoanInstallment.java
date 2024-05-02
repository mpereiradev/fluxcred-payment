package dev.matheuspereira.fluxcred.payment.domain.model;

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
public class LoanInstallment {
  private Integer id;
  private Loan loan;
  private int installmentNumber;
  private BigDecimal amount;
  private LocalDate dueDate;
  private LocalDateTime paymentDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
