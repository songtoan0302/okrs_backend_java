package org.aibles.okrs.core_business.service;

import org.aibles.okrs.core_business.dto.request.CreateOkrsRequest;
import org.aibles.okrs.core_business.dto.response.ObjectiveResponsesDTO;

public interface OkrsService {

  ObjectiveResponsesDTO create(CreateOkrsRequest createOkrsRequest);
}
