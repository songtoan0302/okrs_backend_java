package org.aibles.okrs.core_business.dto.response;

import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.aibles.okrs.core_business.contants.Unit;
import org.aibles.okrs.core_business.validation.ModelValidator;

@Data
public class KeyResultResponseDTO extends ModelValidator<KeyResultResponseDTO> {
  @NotBlank private String id;

  @NotBlank private String status;

  @NotBlank private String content;

  @NotNull private Long target;

  @NotNull private Long currentAchievement;

  private Unit unit;

  private Instant deadlineAt;
}
