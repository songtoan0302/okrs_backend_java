package org.aibles.okrs.core_exception.exception;

import org.springframework.http.HttpStatus;

/**
 * @author toanns
 */
public class NotFoundException extends BaseException {

  private static final String MESSAGE_I18N_NOT_FOUND = "message.not-found";
  private static final String CODE_NOT_FOUND_EXCEPTION = "org.aibles.core_exception.exception.NotFoundException";

  public NotFoundException(Object id) {
    setStatus(HttpStatus.NOT_FOUND.value());
    setCode(CODE_NOT_FOUND_EXCEPTION);
    addParam(MESSAGE_I18N_NOT_FOUND, id);
    setKeyMessage(MESSAGE_I18N_NOT_FOUND);
  }
}
