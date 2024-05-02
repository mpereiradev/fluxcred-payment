package dev.matheuspereira.fluxcred.payment.domain.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 2427882456503494680L;
  private final int httpStatus;
  private final String errorCode;

  public BusinessException(String message, int httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
    this.errorCode = "";
  }

  public BusinessException(String message, int httpStatus, String errorCode) {
    super(message);
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
  }
}
