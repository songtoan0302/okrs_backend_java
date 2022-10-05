package org.aibles.okrs.core_business.validation;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okrs.core_exception.exception.BadRequestException;

@Slf4j
@RequiredArgsConstructor
public class ModelValidator<T> {

  private static final Validator VALIDATOR =
      Validation.buildDefaultValidatorFactory().getValidator();

  public Map<String, Object> isValid() {
    var errorMap = new HashMap<String, Object>();

    var violations = VALIDATOR.validate(this);
    if (!violations.isEmpty()) {
      log.info("(isValid){} --> INVALID", this.getClass().getTypeName());

      for (var violation : violations) {
        errorMap.put(getField(violation), violation.getMessage());
      }
    }
    return errorMap;
  }

  public void validate() {
    var errorMap = new HashMap<String, Object>();

    var violations = VALIDATOR.validate(this);
    if (!violations.isEmpty()) {
      log.info("(isValid){} --> INVALID", this.getClass().getTypeName());

      for (var violation : violations) {
        errorMap.put(getField(violation), violation.getMessage());
      }
    }

    if (!errorMap.isEmpty()) {
      log.error("(isValid){}", errorMap);
      throw new BadRequestException(errorMap);
    }
  }

  private String getField(ConstraintViolation<ModelValidator<T>> violation) {
    return violation.getPropertyPath().toString();
  }
}
