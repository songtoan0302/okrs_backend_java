package org.aibles.okrs.core_business.dto.request;

import java.time.Instant;
import java.util.HashMap;
import lombok.Data;
import org.aibles.okrs.core_business.contants.Unit;
import org.aibles.okrs.core_exception.exception.BadRequestException;

@Data
public class UpdateKeyResultRequest {

  private String content;

  private Long target;

  private Long currentAchievement;

  private Unit unit;

  private Instant deadlineAt;

  private String status;

}
