package dev.matheuspereira.fluxcred.payment.application.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.matheuspereira.fluxcred.payment.application.config.RabbitMQConfig;
import dev.matheuspereira.fluxcred.payment.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.payment.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import dev.matheuspereira.fluxcred.payment.domain.ports.driver.ILoanInstallmentService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles="USER")
class LoanInstallmentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ILoanInstallmentService loanInstallmentService;

  @MockBean
  private RabbitMQConfig rabbitMQConfig;


  @Test
  void testCreateInstallmentsSuccess() throws Exception {
    doNothing().when(loanInstallmentService).createInstallments(any(Loan.class));
    mockMvc.perform(post("/installments/create/{loanId}", 1))
        .andExpect(status().isOk())
        .andExpect(content().string("Loan installments generated"));
  }

  @Test
  void testCreateInstallmentsFailure() throws Exception {
    doThrow(new BusinessException("Invalid loan data", 422))
        .when(loanInstallmentService)
        .createInstallments(any(Loan.class));
    mockMvc.perform(post("/installments/create/{loanId}", 1))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void testListInstallmentsSuccess() throws Exception {
    List<LoanInstallment> installments = new ArrayList<>();
    installments.add(new LoanInstallment());
    when(loanInstallmentService.listInstallment(anyInt())).thenReturn(installments);
    mockMvc.perform(get("/installments/list/{loanId}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0]").exists());
  }

  @Test
  void testListInstallmentsEmpty() throws Exception {
    when(loanInstallmentService.listInstallment(anyInt())).thenReturn(new ArrayList<>());
    mockMvc.perform(get("/installments/list/{loanId}", 1))
        .andExpect(status().isOk());
  }

  @Test
  void testPaymentInstallmentSuccess() throws Exception {
    LoanInstallment installment = new LoanInstallment();
    when(loanInstallmentService.paymentInstallment(anyInt())).thenReturn(installment);
    mockMvc.perform(post("/installments/payment/{installmentId}", 1))
        .andExpect(status().isOk());
  }

  @Test
  void testPaymentInstallmentNotFound() throws Exception {
    when(loanInstallmentService.paymentInstallment(anyInt())).thenThrow(new NotFoundException());
    mockMvc.perform(post("/installments/payment/{installmentId}", 999))
        .andExpect(status().isNotFound());
  }
}
