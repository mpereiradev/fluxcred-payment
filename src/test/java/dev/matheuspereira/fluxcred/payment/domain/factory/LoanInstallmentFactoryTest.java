package dev.matheuspereira.fluxcred.payment.domain.factory;

import static org.junit.jupiter.api.Assertions.*;

import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanInstallmentFactoryTest {

  @InjectMocks private LoanInstallmentFactory loanInstallmentFactory;

  @Test
  void testEqualInstallments() {
    Loan loan = new Loan();
    loan.setAmount(300.00);
    loan.setNumberOfInstallments(3);
    loan.setFirstPaymentDate(LocalDate.of(2024, 1, 1));

    List<LoanInstallment> installments = loanInstallmentFactory.generateInstallments(loan);
    assertEquals(3, installments.size());
    assertTrue(installments.stream().allMatch(i -> i.getAmount().equals(new BigDecimal("100.00"))));
  }

  @Test
  void testInstallmentsWithRounding() {
    Loan loan = new Loan();
    loan.setAmount(1000.00);
    loan.setNumberOfInstallments(3);
    loan.setFirstPaymentDate(LocalDate.now());

    List<LoanInstallment> installments = loanInstallmentFactory.generateInstallments(loan);
    assertEquals(3, installments.size());
    assertEquals(new BigDecimal("333.34"), installments.get(0).getAmount());
    assertEquals(new BigDecimal("333.33"), installments.get(1).getAmount());
    assertEquals(new BigDecimal("333.33"), installments.get(2).getAmount());
    assertEquals(
        new BigDecimal("1000.00"),
        installments.stream()
            .map(LoanInstallment::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
  }

  @Test
  void testInstallmentsDateSequence() {
    Loan loan = new Loan();
    loan.setAmount(900.00);
    loan.setNumberOfInstallments(3);
    loan.setFirstPaymentDate(LocalDate.of(2024, 1, 15));

    List<LoanInstallment> installments = loanInstallmentFactory.generateInstallments(loan);
    assertEquals(LocalDate.of(2024, 1, 15), installments.get(0).getDueDate());
    assertEquals(LocalDate.of(2024, 2, 15), installments.get(1).getDueDate());
    assertEquals(LocalDate.of(2024, 3, 15), installments.get(2).getDueDate());
  }

  @Test
  void testInstallmentsWithHighPrecisionAmount() {
    Loan loan = new Loan();
    loan.setAmount(1000.01);
    loan.setNumberOfInstallments(3);
    loan.setFirstPaymentDate(LocalDate.now());

    List<LoanInstallment> installments = loanInstallmentFactory.generateInstallments(loan);
    assertEquals(3, installments.size());
    assertEquals(new BigDecimal("333.34"), installments.get(0).getAmount());
    assertEquals(new BigDecimal("333.34"), installments.get(1).getAmount());
    assertEquals(new BigDecimal("333.33"), installments.get(2).getAmount());
    assertEquals(
        new BigDecimal("1000.01"),
        installments.stream()
            .map(LoanInstallment::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
  }

  @Test
  void testSingleInstallment() {
    Loan loan = new Loan();
    loan.setAmount(500.00);
    loan.setNumberOfInstallments(1);
    loan.setFirstPaymentDate(LocalDate.now());

    List<LoanInstallment> installments = loanInstallmentFactory.generateInstallments(loan);
    assertEquals(1, installments.size());
    assertEquals(new BigDecimal("500.00"), installments.get(0).getAmount());
  }
}
