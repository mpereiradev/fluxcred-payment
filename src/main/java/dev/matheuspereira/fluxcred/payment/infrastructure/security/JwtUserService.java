package dev.matheuspereira.fluxcred.payment.infrastructure.security;

import dev.matheuspereira.fluxcred.payment.infrastructure.entity.UserEntity;
import dev.matheuspereira.fluxcred.payment.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserService implements UserDetailsService {
  private final UserJpaRepository userJpaRepository;

  @Override
  public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
    return userJpaRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
