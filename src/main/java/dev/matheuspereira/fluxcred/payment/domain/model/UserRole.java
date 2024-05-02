package dev.matheuspereira.fluxcred.payment.domain.model;

import lombok.Getter;

@Getter
public enum UserRole {
  USER("Usuário comun"),
  ADMIN("Usuário admin");

  private String description;

  UserRole(String description) {
    this.description = description;
  }


  public static UserRole fromKey(String key) {
    for (UserRole type : UserRole.values()) {
      if (type.name().equalsIgnoreCase(key)) {
        return type;
      }
    }

    throw new IllegalArgumentException("No valid identifier type found for key: " + key);
  }
}