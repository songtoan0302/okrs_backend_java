package org.aibles.okrs.core_business.service.impl;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okrs.core_business.dto.request.CreateObjectiveRequest;
import org.aibles.okrs.core_business.dto.request.CreateOkrsRequest;
import org.aibles.okrs.core_business.dto.response.KeyResultResponseDTO;
import org.aibles.okrs.core_business.dto.response.ObjectiveDetailResponseDTO;
import org.aibles.okrs.core_business.dto.response.ObjectiveResponsesDTO;
import org.aibles.okrs.core_business.service.KeyResultService;
import org.aibles.okrs.core_business.service.ObjectiveService;
import org.aibles.okrs.core_business.service.OkrsService;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class OkrsServiceImpl implements OkrsService {

  private final ObjectiveService objectiveService;
  private final KeyResultService keyResultService;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public ObjectiveResponsesDTO create(CreateOkrsRequest createOkrsRequest) {
    log.info("(create)createOkrsRequest: {}", createOkrsRequest);
    var objective = createObjective(createOkrsRequest);
    Set<KeyResultResponseDTO> keyResultResponseDTOS = createKeyResults(objective.getId(), createOkrsRequest);

    var response = modelMapper.map(objective, ObjectiveResponsesDTO.class);
    response.setKeyResult(keyResultResponseDTOS);
    return response;
  }

  private ObjectiveDetailResponseDTO createObjective(CreateOkrsRequest createOkrsRequest) {
    var objective = modelMapper.map(createOkrsRequest, CreateObjectiveRequest.class);
    return objectiveService.create(objective);
  }

  private Set<KeyResultResponseDTO> createKeyResults(String objectiveId,
      CreateOkrsRequest createOkrsRequest) {
    Set<KeyResultResponseDTO> keyResultResponseDTOS = new HashSet<>();

    createOkrsRequest
        .getListKeyResult()
        .forEach(
            createKeyResultRequest -> {
              createKeyResultRequest.setObjectiveId(objectiveId);
              keyResultResponseDTOS.add(keyResultService.create(createKeyResultRequest));
            });

    return keyResultResponseDTOS;
  }
}
