package org.aibles.okrs.core_exception.controller.advice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okrs.core_exception.component.MessageHelper;
import org.aibles.okrs.core_exception.exception.BaseException;
import org.aibles.okrs.core_exception.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ToanNS
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

  private final MessageHelper messageHelper;

  @ExceptionHandler(value = {BaseException.class})
  public ResponseEntity<ExceptionResponse> baseExceptionHandler(BaseException error) {
    log.error("(Exception) errorCode: {}", error.getCode());
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    exceptionResponse.setError(messageHelper.getMessage(error.getKeyMessage()));
    exceptionResponse.setMessage(error.getParams().get(error.getKeyMessage()));
    exceptionResponse.setTimestamp(Instant.now());
    return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(error.getStatus()));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception
        .getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    log.info("(inValid): {}", errors);
    return errors;
  }
}
