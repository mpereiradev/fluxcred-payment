package dev.matheuspereira.fluxcred.payment.domain.ports.driven;

import java.util.Optional;

public interface IBaseRepository<T> {
  T save(T model);
  Optional<T> findById(Integer id);
  boolean existsById(Integer id);
  void delete(Integer id);
}
