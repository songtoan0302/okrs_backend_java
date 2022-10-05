package org.aibles.okrs.core_business.dto.response;

import java.time.Instant;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.aibles.okrs.core_business.contants.Status;
import org.aibles.okrs.core_business.validation.ModelValidator;

@Data
public class ObjectiveResponsesDTO extends ModelValidator<ObjectiveResponsesDTO> {
  @NotBlank private String id;

  @NotBlank private String type;

  @NotBlank private String content;

  @NotBlank private String reason;

  private Status status;

  private Instant deadlineAt;

  private Set<KeyResultResponseDTO> keyResult;
}
