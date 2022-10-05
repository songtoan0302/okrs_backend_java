package org.aibles.okrs.core_business.service;

import org.aibles.okrs.core_business.dto.request.CreateKeyResultRequest;
import org.aibles.okrs.core_business.dto.request.ListKeyResultCriteria;
import org.aibles.okrs.core_business.dto.request.UpdateKeyResultRequest;
import org.aibles.okrs.core_business.dto.response.KeyResultResponseDTO;
import org.springframework.data.domain.Page;

public interface KeyResultService {

  void delete(String id);

  void deleteByObjectiveId(String objectiveId);

  boolean existByObjectiveId(String objectiveId);

  KeyResultResponseDTO create(CreateKeyResultRequest createKeyResultRequest);

  KeyResultResponseDTO update(String id, UpdateKeyResultRequest updateKeyResultRequest);

  KeyResultResponseDTO get(String id);

  Page<KeyResultResponseDTO> filter(ListKeyResultCriteria listKeyResultCriteria);
}
