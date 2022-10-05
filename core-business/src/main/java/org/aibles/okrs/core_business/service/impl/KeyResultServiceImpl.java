package org.aibles.okrs.core_business.service.impl;

import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okrs.core_business.component.MappingHelper;
import org.aibles.okrs.core_business.contants.Status;
import org.aibles.okrs.core_business.dto.request.CreateKeyResultRequest;
import org.aibles.okrs.core_business.dto.request.ListKeyResultCriteria;
import org.aibles.okrs.core_business.dto.request.UpdateKeyResultRequest;
import org.aibles.okrs.core_business.dto.response.KeyResultResponseDTO;
import org.aibles.okrs.core_business.entity.KeyResult;
import org.aibles.okrs.core_business.repository.KeyResultRepository;
import org.aibles.okrs.core_business.service.KeyResultService;
import org.aibles.okrs.core_business.service.ObjectiveService;
import org.aibles.okrs.core_exception.component.MessageHelper;
import org.aibles.okrs.core_exception.exception.BadRequestException;
import org.aibles.okrs.core_exception.exception.ConflictException;
import org.aibles.okrs.core_exception.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class KeyResultServiceImpl implements KeyResultService {

  private static final int KEY_RESULT_REACHED_LIMIT = 5;
  private static final String KEY_MESSAGE_RESULT_REACHED_LIMIT = "message.limited-key-result";
  private static final String KEY_MESSAGE_DEADLINE_INVALID_GREATER = "message.deadline-invalid-greater";
  private static final String KEY_MESSAGE_DEADLINE_INVALID_LESS = "message.deadline-invalid-less";

  private static final String KEY_MESSAGE_CONFLICT_CONTENT = "message.conflict-content";
  private static final int RESULT_RETURN_WHEN_LESS_THAN_OR_EQUALS = 0;

  private final KeyResultRepository repository;

  private final MappingHelper mappingHelper;

  private final ObjectiveService objectiveService;

  private final MessageHelper messageHelper;

  @CacheEvict(value = "key-result", key = "#id")
  @Override
  @Transactional
  public void delete(String id) {
    log.info("(delete)id: {}", id);
    if (!repository.existsById(id)) {
      throw new NotFoundException(id);
    }
    repository.deleteById(id);
  }

  @CacheEvict
  @Override
  @Transactional
  public void deleteByObjectiveId(String objectiveId) {
    log.info("(deleteByObjectiveId)objectiveId: {}", objectiveId);
    repository.deleteByObjectiveId(objectiveId);
  }

  @Override
  public boolean existByObjectiveId(String objectiveId) {
    log.info("(existByObjectiveId)objectiveId: {}", objectiveId);
    return repository.existsByObjectiveId(objectiveId);
  }

  @Override
  @Transactional
  public KeyResultResponseDTO create(CreateKeyResultRequest createKeyResultRequest) {
    log.info("(create)createKeyResultRequest: {}", createKeyResultRequest);

    var objectiveResponseDTO = objectiveService.get(createKeyResultRequest.getObjectiveId());

    validateInputData(
        createKeyResultRequest.getContent(),
        createKeyResultRequest.getDeadlineAt(),
        objectiveResponseDTO.getDeadlineAt());

    long count = repository.countByObjectiveId(createKeyResultRequest.getObjectiveId());
    if (count > KEY_RESULT_REACHED_LIMIT - 1) {
      throw new BadRequestException(messageHelper.getMessage(KEY_MESSAGE_RESULT_REACHED_LIMIT));
    }

    var keyResult = mappingHelper.map(createKeyResultRequest, KeyResult.class);
    keyResult.validate();
    keyResult.setStatus(Status.NOT_STARTED);
    keyResult = repository.save(keyResult);

    var response = mappingHelper.map(keyResult, KeyResultResponseDTO.class);
    response.validate();
    return response;
  }

  @CachePut(value = "key-result", key = "#id")
  @Override
  @Transactional
  public KeyResultResponseDTO update(String id, UpdateKeyResultRequest updateKeyResultRequest) {
    log.info("(update)id: {}, updateKeyResultRequest: {}", id, updateKeyResultRequest);

    return repository
        .findById(id)
        .map(
            exist -> {
              var objectiveResponseDTO = objectiveService.get(exist.getObjectiveId());
              validateInputData(
                  updateKeyResultRequest.getContent(),
                  updateKeyResultRequest.getDeadlineAt(),
                  objectiveResponseDTO.getDeadlineAt());

              mappingHelper.mapIgnoreNull(updateKeyResultRequest, exist);
              repository.save(exist);

              var response = mappingHelper.map(exist, KeyResultResponseDTO.class);
              response.validate();
              return response;
            })
        .orElseThrow(
            () -> {
              throw new NotFoundException(id);
            });
  }

  @Cacheable(value = "key-result", key = "#id")
  @Override
  @Transactional(readOnly = true)
  public KeyResultResponseDTO get(String id) {
    log.info("(get)id: {}", id);

    return repository
        .findById(id)
        .map(
            exist -> {
              var response = mappingHelper.map(exist, KeyResultResponseDTO.class);
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
  public Page<KeyResultResponseDTO> filter(ListKeyResultCriteria listKeyResultCriteria) {
    log.info("(filter)listKeyResultCriteria: {}", listKeyResultCriteria);
    Page<KeyResult> pageKeyResult =
        repository.findAll(
            listKeyResultCriteria.toPredicate(), listKeyResultCriteria.makePageable());
    return mappingHelper.mapPage(pageKeyResult, KeyResultResponseDTO.class);
  }

  private void validateInputData(String content, Instant inputTime, Instant limitTime) {

    if (Objects.nonNull(inputTime)) {
      var checkMinTime = inputTime.compareTo(Instant.now());
      if (checkMinTime <= RESULT_RETURN_WHEN_LESS_THAN_OR_EQUALS) {
        throw new BadRequestException(messageHelper.getMessage(KEY_MESSAGE_DEADLINE_INVALID_GREATER));
      }

      if (Objects.nonNull(limitTime)) {
        var checkMaxTime = inputTime.compareTo(limitTime);
        if (checkMaxTime > RESULT_RETURN_WHEN_LESS_THAN_OR_EQUALS) {
          throw new BadRequestException(messageHelper.getMessage(KEY_MESSAGE_DEADLINE_INVALID_LESS));
        }
      }
    }

    if (repository.existsByContent(content)) {
      throw new ConflictException(messageHelper.getMessage(KEY_MESSAGE_CONFLICT_CONTENT));
    }
  }
}
