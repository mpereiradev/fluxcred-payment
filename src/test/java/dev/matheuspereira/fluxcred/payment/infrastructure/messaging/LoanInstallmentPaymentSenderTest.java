package dev.matheuspereira.fluxcred.payment.infrastructure.messaging;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class LoanInstallmentPaymentSenderTest {

  @Mock private RabbitTemplate rabbitTemplate;
  @Mock private ObjectMapper objectMapper;

  @InjectMocks private LoanInstallmentPaymentSender paymentSender;

  private LoanInstallment installment;

  @BeforeEach
  void setUp() {
    installment = new LoanInstallment();
    installment.setId(1);

    ReflectionTestUtils.setField(paymentSender, "exchange", "fluxcredExchange");
    ReflectionTestUtils.setField(paymentSender, "routingKey", "loanInstallmentPayment");
  }

  @Test
  void sendLoanInstallPaymentMessage_success() throws Exception {
    String jsonMessage = "{\"id\": 1}";
    when(objectMapper.writeValueAsString(installment)).thenReturn(jsonMessage);
    doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());

    paymentSender.sendLoanInstallPaymentMessage(installment);

    verify(rabbitTemplate)
        .convertAndSend("fluxcredExchange", "loanInstallmentPayment", jsonMessage);
    verifyNoMoreInteractions(rabbitTemplate);
  }

  @Test
  void sendLoanInstallPaymentMessage_jsonProcessingException() throws Exception {
    when(objectMapper.writeValueAsString(installment))
        .thenThrow(new JsonProcessingException("Error") {});

    paymentSender.sendLoanInstallPaymentMessage(installment);

    verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), anyString());
  }

  @Test
  void sendLoanInstallPaymentMessage_generalException() throws Exception {
    String jsonMessage = "{\"id\": 1}";
    when(objectMapper.writeValueAsString(installment)).thenReturn(jsonMessage);
    doThrow(new RuntimeException("Error sending message"))
        .when(rabbitTemplate)
        .convertAndSend(anyString(), anyString(), anyString());

    paymentSender.sendLoanInstallPaymentMessage(installment);

    verify(rabbitTemplate)
        .convertAndSend("fluxcredExchange", "loanInstallmentPayment", jsonMessage);
    verifyNoMoreInteractions(rabbitTemplate);
  }
}
