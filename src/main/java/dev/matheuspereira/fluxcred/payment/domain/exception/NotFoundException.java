package dev.matheuspereira.fluxcred.payment.domain.exception;

public class NotFoundException extends BusinessException {
  public NotFoundException() {
    super("Not found the register", 404);
  }
}
