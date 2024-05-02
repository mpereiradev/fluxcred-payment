package dev.matheuspereira.fluxcred.payment.domain.ports.driver;

import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;

import java.util.List;

public interface ILoanInstallmentService {
  void createInstallments(Loan loan);
  List<LoanInstallment> listInstallment(Integer loanId);
  LoanInstallment paymentInstallment(Integer installmentId);
}
