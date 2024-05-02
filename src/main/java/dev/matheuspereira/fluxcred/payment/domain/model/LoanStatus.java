package dev.matheuspereira.fluxcred.payment.domain.model;

import lombok.Getter;

@Getter
public enum LoanStatus {
  STARTED("Iniciado"),
  AWAITING_SIGNATURE("Aguardando assinatura do cliente"),
  AWAITING_APPROVAL("Aguardando aprovação do gerente"),
  DISAPPROVED("Reprovado"),
  APPROVED("Aprovado"),
  CANCELED("Cancelado"),
  IN_PROGRESS("Em andamento"),
  DELINQUENCY("Inadimplente"),
  FINISHED("Finalizado");

  private String description;

  LoanStatus(String description) {
    this.description = description;
  }

  public static LoanStatus fromKey(String key) {
    for (LoanStatus type : LoanStatus.values()) {
      if (type.name().equalsIgnoreCase(key)) {
        return type;
      }
    }

    throw new IllegalArgumentException("No valid loan status found for key: " + key);
  }
}