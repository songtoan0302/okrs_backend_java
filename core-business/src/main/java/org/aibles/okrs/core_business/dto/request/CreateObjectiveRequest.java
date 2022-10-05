package org.aibles.okrs.core_business.dto.request;

import java.time.Instant;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateObjectiveRequest {
  @NotBlank(message = "type can't blank!")
  private String type;

  @NotBlank(message = "content can't blank!")
  private String content;

  @NotBlank(message = "reason can't blank!")
  private String reason;

  private Instant deadlineAt;
}
