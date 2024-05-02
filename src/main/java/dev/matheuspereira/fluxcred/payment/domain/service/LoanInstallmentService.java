package dev.matheuspereira.fluxcred.payment.domain.service;

import static java.util.Objects.nonNull;

import dev.matheuspereira.fluxcred.payment.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.payment.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.payment.domain.factory.LoanInstallmentFactory;
import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import dev.matheuspereira.fluxcred.payment.domain.ports.driven.ILoanInstallmentPaymentSender;
import dev.matheuspereira.fluxcred.payment.domain.ports.driven.ILoanInstallmentRepository;
import dev.matheuspereira.fluxcred.payment.domain.ports.driven.ILoanRepository;
import dev.matheuspereira.fluxcred.payment.domain.ports.driver.ILoanInstallmentService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanInstallmentService implements ILoanInstallmentService {
  private final LoanInstallmentFactory installmentFactory;
  private final ILoanRepository loanRepository;
  private final ILoanInstallmentRepository loanInstallmentRepository;
  private final ILoanInstallmentPaymentSender loanInstallmentPaymentSender;

  @Override
  public void createInstallments(Loan loan) {
    loan = loanRepository.findById(loan.getId()).orElseThrow(NotFoundException::new);

    List<LoanInstallment> installmentsExists =
        loanInstallmentRepository.findAllByLoanId(loan.getId());
    if (!installmentsExists.isEmpty()) {
      throw new BusinessException("This loan already has installments generated", 412);
    }

    List<LoanInstallment> loanInstallments = installmentFactory.generateInstallments(loan);
    try {
      loanInstallmentRepository.saveAll(loanInstallments);
    } catch (Exception e) {
      log.error("There was an internal error when saving the installments", e);
      throw new BusinessException("There was an internal error when saving the installments", 500);
    }
  }

  @Override
  public List<LoanInstallment> listInstallment(Integer loanId) {
    return loanInstallmentRepository.findAllByLoanId(loanId);
  }

  @Override
  public LoanInstallment paymentInstallment(Integer installmentId) {
    LoanInstallment installment = loanInstallmentRepository.findById(installmentId);
    if (nonNull(installment.getPaymentDate())) {
      throw new BusinessException("This installment has already been paid", 422);
    }

    installment.setPaymentDate(LocalDateTime.now());
    try {
      installment = loanInstallmentRepository.save(installment);
      loanInstallmentPaymentSender.sendLoanInstallPaymentMessage(installment);
      return installment;
    } catch (Exception e) {
      log.error("Unable to pay this installment", e);
      throw new BusinessException("Unable to pay this installment", 500);
    }
  }
}
