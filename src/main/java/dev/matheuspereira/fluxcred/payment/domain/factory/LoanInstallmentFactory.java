package dev.matheuspereira.fluxcred.payment.domain.factory;

import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LoanInstallmentFactory {
  public List<LoanInstallment> generateInstallments(Loan loan) {
    List<LoanInstallment> installments = new ArrayList<>();
    BigDecimal totalAmount = BigDecimal.valueOf(loan.getAmount());
    int numberOfInstallments = loan.getNumberOfInstallments();

    BigDecimal baseAmount = totalAmount.divide(BigDecimal.valueOf(numberOfInstallments), 2, RoundingMode.DOWN);
    BigDecimal totalCalculatedAmount = baseAmount.multiply(BigDecimal.valueOf(numberOfInstallments));
    BigDecimal difference = totalAmount.subtract(totalCalculatedAmount);
    BigDecimal oneCent = new BigDecimal("0.01");

    for (int i = 0; i < numberOfInstallments; i++) {
      BigDecimal installmentAmount = baseAmount;

      if (difference.compareTo(BigDecimal.ZERO) > 0) {
        installmentAmount = installmentAmount.add(oneCent);
        difference = difference.subtract(oneCent);
      }

      LocalDate dueDate = loan.getFirstPaymentDate().plusMonths(i);
      LoanInstallment installment = LoanInstallment.builder()
          .loan(loan)
          .installmentNumber(i + 1)
          .amount(installmentAmount)
          .dueDate(dueDate)
          .build();
      installments.add(installment);
    }
    return installments;
  }
}
