package dev.matheuspereira.fluxcred.payment.application.web;

import dev.matheuspereira.fluxcred.payment.application.data.response.LoanInstallmentResponse;
import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import dev.matheuspereira.fluxcred.payment.domain.ports.driver.ILoanInstallmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/installments")
@RequiredArgsConstructor
@Tag(name = "Installment Management", description = "APIs for managing loan installments")
public class LoanInstallmentController {

  private final ILoanInstallmentService loanInstallmentService;
  private final ModelMapper modelMapper;

  @PostMapping("/create/{loanId}")
  @Operation(summary = "Create loan installments")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Installments created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid loan data")
      })
  public ResponseEntity<String> createInstallments(@PathVariable Integer loanId) {
    Loan loan = Loan.builder().id(loanId).build();
    loanInstallmentService.createInstallments(loan);
    return ResponseEntity.ok("Loan installments generated");
  }

  @GetMapping("/list/{loanId}")
  @Operation(summary = "List installments for a loan")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Installments listed successfully"),
        @ApiResponse(responseCode = "404", description = "Loan not found")
      })
  public ResponseEntity<List<LoanInstallmentResponse>> listInstallments(
      @PathVariable Integer loanId) {
    List<LoanInstallmentResponse> installments =
        loanInstallmentService.listInstallment(loanId).stream()
            .map(i -> modelMapper.map(i, LoanInstallmentResponse.class))
            .toList();
    return ResponseEntity.ok(installments);
  }

  @PostMapping("/payment/{installmentId}")
  @Operation(summary = "Record payment for an installment")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Payment recorded successfully"),
        @ApiResponse(responseCode = "404", description = "Installment not found")
      })
  public ResponseEntity<LoanInstallment> paymentInstallment(@PathVariable Integer installmentId) {
    LoanInstallment installment = loanInstallmentService.paymentInstallment(installmentId);
    return ResponseEntity.ok(installment);
  }
}
