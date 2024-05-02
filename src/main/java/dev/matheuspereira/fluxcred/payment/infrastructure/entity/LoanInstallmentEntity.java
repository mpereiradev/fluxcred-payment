package dev.matheuspereira.fluxcred.payment.infrastructure.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan_installments")
public class LoanInstallmentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loan_id", nullable = false)
  private LoanEntity loan;

  @Column(name = "loan_id", insertable = false, updatable = false)
  private Integer loanId;

  @Column(nullable = false)
  private int installmentNumber;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false)
  private LocalDate dueDate;

  @Column private LocalDateTime paymentDate;

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}
