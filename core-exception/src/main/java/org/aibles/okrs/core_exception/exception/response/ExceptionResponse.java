package org.aibles.okrs.core_exception.exception.response;

import java.time.Instant;

/**
 * @author toanns
 */
public class ExceptionResponse {
  private Instant timestamp;
  private String error;
  private Object message;

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public Object getMessage() {
    return message;
  }

  public void setMessage(Object message) {
    this.message = message;
  }
}
