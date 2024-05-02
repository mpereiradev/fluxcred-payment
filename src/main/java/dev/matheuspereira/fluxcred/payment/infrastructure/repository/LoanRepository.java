package dev.matheuspereira.fluxcred.payment.infrastructure.repository;

import dev.matheuspereira.fluxcred.payment.domain.exception.BusinessException;
import dev.matheuspereira.fluxcred.payment.domain.model.Loan;
import dev.matheuspereira.fluxcred.payment.domain.ports.driven.ILoanRepository;
import dev.matheuspereira.fluxcred.payment.infrastructure.jpa.LoanJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Repository
@RequiredArgsConstructor
public class LoanRepository implements ILoanRepository {
  private final LoanJpaRepository loanJpaRepository;
  private final ModelMapper modelMapper;

  @Override
  public Loan save(Loan loan) {
    throw new BusinessException("This resource not implemented, please use FluxCred Code", 412);
  }

  @Override
  public Optional<Loan> findById(Integer id) {
    return loanJpaRepository.findById(id).map(l -> modelMapper.map(l, Loan.class));
  }

  @Override
  public boolean existsById(Integer id) {
    return loanJpaRepository.existsById(id);
  }

  @Override
  public void delete(Integer id) {
    throw new BusinessException("This resource not implemented, please use FluxCred Code", 412);
  }
}
