package dev.matheuspereira.fluxcred.payment.infrastructure.entity;

import dev.matheuspereira.fluxcred.payment.domain.model.LoanStatus;
import jakarta.persistence.*;
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
@Table(name = "loans")
public class LoanEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, name = "person_identifier")
  private String personIdentifier;

  @Column(nullable = false)
  private double amount;

  @Column(nullable = false)
  private int numberOfInstallments;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LoanStatus status;

  @Column private LocalDate firstPaymentDate;

  @Column private LocalDateTime approvalDate;

  @Column private LocalDateTime signingDate;

  @Column private LocalDateTime delinquencyDate;

  @Column private LocalDateTime nextPaymentDate;

  @Column private LocalDateTime completionDate;

  @Column private LocalDateTime cancellationDate;

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}
