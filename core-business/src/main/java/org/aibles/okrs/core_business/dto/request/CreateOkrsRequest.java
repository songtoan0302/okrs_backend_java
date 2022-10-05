package org.aibles.okrs.core_business.dto.request;

import java.util.List;
import lombok.Data;

@Data
public class CreateOkrsRequest extends CreateObjectiveRequest {
  private List<CreateKeyResultRequest> listKeyResult;
}
