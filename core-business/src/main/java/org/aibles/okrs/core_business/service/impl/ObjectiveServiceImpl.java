package org.aibles.okrs.core_business.service.impl;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okrs.core_business.component.MappingHelper;
import org.aibles.okrs.core_business.contants.Status;
import org.aibles.okrs.core_business.dto.request.CreateObjectiveRequest;
import org.aibles.okrs.core_business.dto.request.ListObjectiveCriteria;
import org.aibles.okrs.core_business.dto.request.UpdateObjectiveRequest;
import org.aibles.okrs.core_business.dto.response.ObjectiveDetailResponseDTO;
import org.aibles.okrs.core_business.dto.response.ObjectiveResponsesDTO;
import org.aibles.okrs.core_business.entity.Objective;
import org.aibles.okrs.core_business.repository.ObjectiveRepository;
import org.aibles.okrs.core_business.service.KeyResultService;
import org.aibles.okrs.core_business.service.ObjectiveService;
import org.aibles.okrs.core_exception.component.MessageHelper;
import org.aibles.okrs.core_exception.exception.BadRequestException;
import org.aibles.okrs.core_exception.exception.ConflictException;
import org.aibles.okrs.core_exception.exception.NotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class ObjectiveServiceImpl implements ObjectiveService {

  private static final int RESULT_RETURN_WHEN_LESS_THAN_OR_EQUALS = 0;
  private static final String KEY_MESSAGE_DEADLINE_INVALID = "message.deadline-invalid-greater";
  private static final String KEY_MESSAGE_CONFLICT_CONTENT = "message.conflict-content";

  private final ObjectiveRepository repository;

  private final KeyResultService keyResultService;

  private final MappingHelper mappingHelper;

  private final MessageHelper messageHelper;

  @CacheEvict(value = "objective", key = "#id")
  @Override
  @Transactional
  public void delete(String id) {
    log.info("(delete)id: {}", id);
    if (!repository.existsById(id)) {
      throw new NotFoundException(id);
    }
    if (keyResultService.existByObjectiveId(id)) {
      keyResultService.deleteByObjectiveId(id);
    }
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public ObjectiveDetailResponseDTO create(CreateObjectiveRequest createObjectiveRequest) {
    log.info("(update)createObjectiveRequest: {}", createObjectiveRequest);

    validateInputData(createObjectiveRequest.getContent(), createObjectiveRequest.getDeadlineAt());

    if(repository.existsByContent(createObjectiveRequest.getContent())) {
      throw new ConflictException("Content "+ createObjectiveRequest.getContent()+" existed");
    }

    var objective = mappingHelper.map(createObjectiveRequest, Objective.class);
    objective.validate();
    objective.setStatus(Status.NOT_STARTED);
    objective = repository.save(objective);

    var response = mappingHelper.map(objective, ObjectiveDetailResponseDTO.class);
    response.validate();
    return response;
  }

  @CachePut(value = "objective", key = "#id")
  @Override
  @Transactional
  public ObjectiveDetailResponseDTO update(
      String id, UpdateObjectiveRequest updateObjectiveRequest) {
    log.info("(update)id: {}, updateObjectiveRequest: {}", id, updateObjectiveRequest);

    validateInputData(updateObjectiveRequest.getContent(), updateObjectiveRequest.getDeadlineAt());

    return repository
        .findById(id)
        .map(
            exist -> {
              mappingHelper.mapIgnoreNull(updateObjectiveRequest, exist);
              exist = repository.save(exist);

              var response = mappingHelper.map(exist, ObjectiveDetailResponseDTO.class);
              response.validate();
              return response;
            })
        .orElseThrow(
            () -> {
              throw new NotFoundException(id);
            });
  }

  @Cacheable(value = "objective", key = "#id")
  @Override
  @Transactional(readOnly = true)
  public ObjectiveResponsesDTO get(String id) {
    log.info("(get)id: {}", id);
    return repository
        .findById(id)
        .map(
            exist -> {
              var response = mappingHelper.map(exist, ObjectiveResponsesDTO.class);
              response.validate();
              return response;
            })
        .orElseThrow(
            () -> {
              throw new NotFoundException(id);
            });
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existById(String id) {
    log.info("(existById)id: {}", id);
    return repository.existsById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ObjectiveResponsesDTO> filter(ListObjectiveCriteria listObjectiveCriteria) {
    log.info("(filter)listObjectiveCriteria: {}", listObjectiveCriteria);
    Page<Objective> pageObjective =
        repository.findAll(
            listObjectiveCriteria.toPredicate(), listObjectiveCriteria.makePageable());
    return mappingHelper.mapPage(pageObjective, ObjectiveResponsesDTO.class);
  }

  private void validateInputData(String content, Instant inputTime) {

    var value = inputTime.compareTo(Instant.now());
    if(value <= RESULT_RETURN_WHEN_LESS_THAN_OR_EQUALS) {
      throw new BadRequestException(messageHelper.getMessage(KEY_MESSAGE_DEADLINE_INVALID));
    }

    if (repository.existsByContent(content)) {
      throw new ConflictException(messageHelper.getMessage(KEY_MESSAGE_CONFLICT_CONTENT));
    }
  }
}
