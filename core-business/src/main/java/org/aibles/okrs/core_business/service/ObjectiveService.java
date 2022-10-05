package org.aibles.okrs.core_business.service;

import org.aibles.okrs.core_business.dto.request.CreateObjectiveRequest;
import org.aibles.okrs.core_business.dto.request.ListObjectiveCriteria;
import org.aibles.okrs.core_business.dto.request.UpdateObjectiveRequest;
import org.aibles.okrs.core_business.dto.response.ObjectiveDetailResponseDTO;
import org.aibles.okrs.core_business.dto.response.ObjectiveResponsesDTO;
import org.springframework.data.domain.Page;

public interface ObjectiveService {

  void delete(String id);

  ObjectiveDetailResponseDTO create(CreateObjectiveRequest createObjectiveRequest);

  ObjectiveDetailResponseDTO update(String id, UpdateObjectiveRequest updateObjectiveRequest);

  ObjectiveResponsesDTO get(String id);

  boolean existById(String id);

  Page<ObjectiveResponsesDTO> filter(ListObjectiveCriteria listObjectiveCriteria);
}
