package dev.matheuspereira.fluxcred.payment.infrastructure.jpa;

import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import dev.matheuspereira.fluxcred.payment.infrastructure.entity.LoanInstallmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanInstallmentJpaRepository extends CrudRepository<LoanInstallmentEntity, Integer> {
  List<LoanInstallmentEntity> findAllByLoanId(Integer loanId);
}
