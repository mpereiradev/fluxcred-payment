package dev.matheuspereira.fluxcred.payment.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.matheuspereira.fluxcred.payment.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.payment.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.payment.domain.factory.LoanInstallmentFactory;
import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import dev.matheuspereira.fluxcred.payment.domain.ports.driven.ILoanInstallmentPaymentSender;
import dev.matheuspereira.fluxcred.payment.domain.ports.driven.ILoanInstallmentRepository;
import dev.matheuspereira.fluxcred.payment.domain.ports.driven.ILoanRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanInstallmentServiceTest {

  @Mock private LoanInstallmentFactory installmentFactory;
  @Mock private ILoanRepository loanRepository;
  @Mock private ILoanInstallmentRepository loanInstallmentRepository;
  @Mock private ILoanInstallmentPaymentSender loanInstallmentPaymentSender;

  @InjectMocks private LoanInstallmentService loanInstallmentService;

  private Loan loan;
  private LoanInstallment installment;

  @BeforeEach
  void setUp() {
    loan = new Loan();
    loan.setId(1);

    installment = new LoanInstallment();
    installment.setId(1);
    installment.setLoan(loan);
  }

  @Test
  void createInstallmentsWhenLoanExistsAndNoPreviousInstallments() {
    when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
    when(loanInstallmentRepository.findAllByLoanId(loan.getId())).thenReturn(new ArrayList<>());
    doNothing().when(loanInstallmentRepository).saveAll(any(List.class));

    loanInstallmentService.createInstallments(loan);

    verify(loanInstallmentRepository).saveAll(any(List.class));
  }

  @Test
  void createInstallmentsWhenLoanDoesNotExist() {
    when(loanRepository.findById(loan.getId())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> loanInstallmentService.createInstallments(loan));
  }

  @Test
  void createInstallmentsWhenInstallmentsAlreadyExist() {
    when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
    when(loanInstallmentRepository.findAllByLoanId(loan.getId()))
        .thenReturn(List.of(new LoanInstallment()));

    assertThrows(BusinessException.class, () -> loanInstallmentService.createInstallments(loan));
  }

  @Test
  void listInstallmentSuccess() {
    when(loanInstallmentRepository.findAllByLoanId(loan.getId())).thenReturn(List.of(installment));

    List<LoanInstallment> result = loanInstallmentService.listInstallment(loan.getId());

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(installment, result.get(0));
  }

  @Test
  void paymentInstallmentWhenNotPaid() {
    var installmentSave =
        LoanInstallment.builder().id(1).loan(loan).paymentDate(LocalDateTime.now()).build();
    when(loanInstallmentRepository.findById(installment.getId())).thenReturn(installment);
    when(loanInstallmentRepository.save(any())).thenReturn(installmentSave);
    doNothing().when(loanInstallmentPaymentSender).sendLoanInstallPaymentMessage(installmentSave);

    LoanInstallment result = loanInstallmentService.paymentInstallment(installment.getId());

    assertNotNull(result.getPaymentDate());
    verify(loanInstallmentPaymentSender).sendLoanInstallPaymentMessage(installmentSave);
  }

  @Test
  void paymentInstallmentWhenAlreadyPaid() {
    installment.setPaymentDate(LocalDateTime.now());
    when(loanInstallmentRepository.findById(installment.getId())).thenReturn(installment);

    assertThrows(
        BusinessException.class,
        () -> loanInstallmentService.paymentInstallment(installment.getId()));
  }

  @Test
  void paymentInstallmentWhenInstallmentNotFound() {
    when(loanInstallmentRepository.findById(installment.getId()))
        .thenThrow(new NotFoundException());

    assertThrows(
        NotFoundException.class,
        () -> loanInstallmentService.paymentInstallment(installment.getId()));
  }
}
