package org.aibles.okrs.core_business.dto.request;

import java.time.Instant;
import lombok.Data;

@Data
public class UpdateObjectiveRequest {
  private String type;
  private String content;
  private String reason;
  private Instant deadlineAt;
  private String status;
}
