package dev.matheuspereira.fluxcred.payment.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import dev.matheuspereira.fluxcred.payment.domain.ports.driven.ILoanInstallmentPaymentSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanInstallmentPaymentSender implements ILoanInstallmentPaymentSender {
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  @Value("${fluxcred.rabbitmq.exchange}")
  private String exchange;

  @Value("${fluxcred.rabbitmq.loan.installment.payment.routing_key}")
  private String routingKey;

  @Override
  public void sendLoanInstallPaymentMessage(LoanInstallment installment) {
    try {
      String message = objectMapper.writeValueAsString(installment);
      rabbitTemplate.convertAndSend(exchange, routingKey, message);
      log.info("Sent Loan Installment Payment Message: " + message);
    } catch (JsonProcessingException e) {
      log.error("Unable to convert loan to json", e);
    } catch (Exception e) {
      log.error("Error sending loanInstallmentPaymentMessage", e);
    }
  }
}
