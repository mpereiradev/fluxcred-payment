package dev.matheuspereira.fluxcred.payment.infrastructure.security;

import dev.matheuspereira.fluxcred.payment.infrastructure.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public boolean isTokenValid(String token, UserEntity userEntity) {
    final String username = extractUsername(token);
    return (username.equals(userEntity.getUsername())) && !isTokenExpired(token);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
  }

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
  }
}
