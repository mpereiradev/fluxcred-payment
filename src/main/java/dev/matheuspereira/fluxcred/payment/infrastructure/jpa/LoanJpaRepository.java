package dev.matheuspereira.fluxcred.payment.infrastructure.jpa;

import dev.matheuspereira.fluxcred.payment.infrastructure.entity.LoanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanJpaRepository extends CrudRepository<LoanEntity, Integer> {}
