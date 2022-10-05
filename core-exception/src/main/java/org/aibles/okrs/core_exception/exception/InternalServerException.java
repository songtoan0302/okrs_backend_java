package org.aibles.okrs.core_exception.exception;

import org.springframework.http.HttpStatus;

/**
 * @author toanns
 */
public class InternalServerException extends BaseException {
  private static final String MESSAGE_I18N_INTERNAL_SERVER_ERROR = "message.internal-server-exception";
  private static final String CODE_INTERNAL_SERVER_ERROR = "org.aibles.core_exception.exception.InternalServerException";
  public InternalServerException(String error) {
    setCode(CODE_INTERNAL_SERVER_ERROR);
    setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    addParam(MESSAGE_I18N_INTERNAL_SERVER_ERROR, error);
    setKeyMessage(MESSAGE_I18N_INTERNAL_SERVER_ERROR);
  }
}
