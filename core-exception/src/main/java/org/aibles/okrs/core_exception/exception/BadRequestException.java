package org.aibles.okrs.core_exception.exception;

import org.springframework.http.HttpStatus;

/**
 * @author toanns
 */
public class BadRequestException extends BaseException {

  private static final String MESSAGE_I18N_BAD_REQUEST = "message.bad-request";
  private static final String CODE_BAD_REQUEST_EXCEPTION = "org.aibles.okrs.core_exception.exception.BadRequestException";

  public BadRequestException(Object dataInput) {
    setCode(CODE_BAD_REQUEST_EXCEPTION);
    setStatus(HttpStatus.BAD_REQUEST.value());
    addParam(MESSAGE_I18N_BAD_REQUEST, dataInput);
    setKeyMessage(MESSAGE_I18N_BAD_REQUEST);
  }
}
