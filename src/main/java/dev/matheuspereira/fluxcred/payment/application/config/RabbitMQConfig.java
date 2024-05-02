package dev.matheuspereira.fluxcred.payment.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQConfig {

  @Value("${fluxcred.rabbitmq.loan.approval.queue}")
  private String loanApprovalQueue;

  @Value("${fluxcred.rabbitmq.loan.approval.routing_key}")
  private String loanApprovalRoutingKey;

  @Value("${fluxcred.rabbitmq.loan.installment.payment.queue}")
  private String loanInstallmentPaymentQueue;

  @Value("${fluxcred.rabbitmq.loan.installment.payment.routing_key}")
  private String loanInstallmentPaymentRoutingKey;

  @Value("${fluxcred.rabbitmq.exchange}")
  private String exchange;

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  Binding bindingLoanApproval(TopicExchange exchange) {
    Queue queue = new Queue(loanApprovalQueue, true);
    return BindingBuilder.bind(queue).to(exchange).with(loanApprovalRoutingKey);
  }

  @Bean
  Binding bindingLoanInstallmentPayment(TopicExchange exchange) {
    Queue queue = new Queue(loanInstallmentPaymentQueue, true);
    return BindingBuilder.bind(queue).to(exchange).with(loanInstallmentPaymentRoutingKey);
  }
}
