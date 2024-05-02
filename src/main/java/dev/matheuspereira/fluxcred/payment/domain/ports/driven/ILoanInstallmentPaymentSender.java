package dev.matheuspereira.fluxcred.payment.domain.ports.driven;

import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;

public interface ILoanInstallmentPaymentSender {
  void sendLoanInstallPaymentMessage(LoanInstallment installment);
}
