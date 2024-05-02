package dev.matheuspereira.fluxcred.payment.infrastructure.messaging;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.ports.driver.ILoanInstallmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoanApprovalConsumerTest {

  @Mock private ObjectMapper objectMapper;
  @Mock private ILoanInstallmentService loanInstallmentService;

  @InjectMocks private LoanApprovalConsumer consumer;

  private final String jsonMessage = "{\"id\": 1, \"amount\": 1000, \"numberOfInstallments\": 10}";

  @Test
  void testReceiveMessage() throws Exception {
    Loan mockLoan = new Loan();
    when(objectMapper.readValue(jsonMessage, Loan.class)).thenReturn(mockLoan);
    doNothing().when(loanInstallmentService).createInstallments(mockLoan);

    consumer.receiveMessage(jsonMessage);

    verify(objectMapper).readValue(jsonMessage, Loan.class);
    verify(loanInstallmentService).createInstallments(mockLoan);
  }
}
