package dev.matheuspereira.fluxcred.payment.infrastructure.jpa;

import dev.matheuspereira.fluxcred.payment.infrastructure.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends CrudRepository<UserEntity, Integer> {
  Optional<UserEntity> findByEmail(String email);
}
