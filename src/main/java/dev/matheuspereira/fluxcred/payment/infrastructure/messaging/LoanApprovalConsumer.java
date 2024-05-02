package dev.matheuspereira.fluxcred.payment.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.ports.driver.ILoanInstallmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanApprovalConsumer {
  private final ObjectMapper objectMapper;
  private final ILoanInstallmentService loanInstallmentService;

  @RabbitListener(queues = {"${fluxcred.rabbitmq.loan.approval.queue}"})
  public void receiveMessage(@Payload String message) {
    try {
      Loan loan = objectMapper.readValue(message, Loan.class);
      loanInstallmentService.createInstallments(loan);
    } catch (Exception e) {
      log.error("Error processing loanApprovalMessage", e);
    }
  }
}
