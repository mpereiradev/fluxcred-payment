package dev.matheuspereira.fluxcred.payment.domain.ports.driven;

import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import java.util.List;

public interface ILoanInstallmentRepository {
  LoanInstallment findById(Integer installmentId);

  LoanInstallment save(LoanInstallment installment);

  void saveAll(List<LoanInstallment> loanInstallments);
  List<LoanInstallment> findAllByLoanId(Integer loanId);
}
