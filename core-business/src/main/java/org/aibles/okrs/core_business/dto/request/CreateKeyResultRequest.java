package org.aibles.okrs.core_business.dto.request;

import java.time.Instant;
import java.util.HashMap;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.aibles.okrs.core_business.contants.Unit;
import org.aibles.okrs.core_exception.exception.BadRequestException;

@Data
public class CreateKeyResultRequest {

  @NotBlank(message = "objectiveId can't blank!")
  private String objectiveId;

  @NotBlank(message = "content can't blank!")
  private String content;

  private long target;

  private long currentAchievement;

  private Unit unit;

  private Instant deadlineAt;

  public void validateDataTypePrimitive() {
    if (this.target == 0) {
      var valid = new HashMap<String, String>();
      valid.put("target", "target not equals to 0!");
      throw new BadRequestException(valid);
    }
  }
}
