package org.aibles.okrs.core_exception.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

  private static final String MESSAGE_I18N_CONFLICT = "message.conflict";
  private static final String CODE_NOT_FOUND_EXCEPTION = "org.aibles.core_exception.exception.ConflictException";

  public ConflictException(Object error) {
    setStatus(HttpStatus.NOT_FOUND.value());
    setCode(CODE_NOT_FOUND_EXCEPTION);
    addParam(MESSAGE_I18N_CONFLICT, error);
    setKeyMessage(MESSAGE_I18N_CONFLICT);
  }
}
