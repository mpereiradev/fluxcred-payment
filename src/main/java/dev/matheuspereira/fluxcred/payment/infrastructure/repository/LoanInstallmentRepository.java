package dev.matheuspereira.fluxcred.payment.infrastructure.repository;

import dev.matheuspereira.fluxcred.payment.domain.exception.NotFoundException;
import dev.matheuspereira.fluxcred.payment.domain.model.LoanInstallment;
import dev.matheuspereira.fluxcred.payment.domain.ports.driven.ILoanInstallmentRepository;
import dev.matheuspereira.fluxcred.payment.infrastructure.entity.LoanInstallmentEntity;
import dev.matheuspereira.fluxcred.payment.infrastructure.jpa.LoanInstallmentJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Repository
@RequiredArgsConstructor
public class LoanInstallmentRepository implements ILoanInstallmentRepository {
  private final LoanInstallmentJpaRepository loanInstallmentJpaRepository;
  private final ModelMapper modelMapper;

  @Override
  public LoanInstallment findById(Integer installmentId) {
    return loanInstallmentJpaRepository
        .findById(installmentId)
        .map(i -> modelMapper.map(i, LoanInstallment.class))
        .orElseThrow(NotFoundException::new);
  }

  @Override
  public LoanInstallment save(LoanInstallment installment) {
    LoanInstallmentEntity installmentEntity =
        modelMapper.map(installment, LoanInstallmentEntity.class);
    return modelMapper.map(
        loanInstallmentJpaRepository.save(installmentEntity), LoanInstallment.class);
  }

  @Override
  public void saveAll(List<LoanInstallment> loanInstallments) {
    List<LoanInstallmentEntity> entities =
        loanInstallments.stream()
            .map(i -> modelMapper.map(i, LoanInstallmentEntity.class))
            .toList();
    loanInstallmentJpaRepository.saveAll(entities);
  }

  @Override
  public List<LoanInstallment> findAllByLoanId(Integer loanId) {
    return loanInstallmentJpaRepository.findAllByLoanId(loanId).stream()
        .map(i -> modelMapper.map(i, LoanInstallment.class))
        .toList();
  }
}
