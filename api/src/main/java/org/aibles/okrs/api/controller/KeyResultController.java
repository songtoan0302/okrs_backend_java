package org.aibles.okrs.api.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okrs.core_business.dto.request.CreateKeyResultRequest;
import org.aibles.okrs.core_business.dto.request.ListKeyResultCriteria;
import org.aibles.okrs.core_business.dto.request.UpdateKeyResultRequest;
import org.aibles.okrs.core_business.dto.response.KeyResultResponseDTO;
import org.aibles.okrs.core_business.service.KeyResultService;
import org.aibles.okrs.core_business.util.paging.PagingRes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/objective")
public class KeyResultController {

  private final KeyResultService service;

  @PostMapping(path = "/{objectiveId}/key-results")
  @ResponseStatus(HttpStatus.CREATED)
  public KeyResultResponseDTO add( @PathVariable String objectiveId,
      @RequestBody @Validated CreateKeyResultRequest createKeyResultRequest) {
    log.info("(add)createKeyResultRequest: {}", createKeyResultRequest);
    return service.create(createKeyResultRequest);
  }

  @DeleteMapping(path = "/{objectiveId}/key-results/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable("id") String id) {
    log.info("(delete)id: {}", id);
    service.delete(id);
  }

  @GetMapping(path = "/{objectiveId}/key-results/{id}")
  @ResponseStatus(HttpStatus.OK)
  public KeyResultResponseDTO get(@PathVariable("id") String id) {
    log.info("(get)id: {}", id);
    return service.get(id);
  }

  @GetMapping(path = "/key-results")
  @ResponseStatus(HttpStatus.OK)
  public PagingRes<KeyResultResponseDTO> filter(
      @Validated ListKeyResultCriteria listKeyResultCriteria) {
    log.info("(filter)listKeyResultCriteria: {}", listKeyResultCriteria);
    return PagingRes.of(service.filter(listKeyResultCriteria));
  }

  @PutMapping(path = "/{objectiveId}/key-results/{id}")
  public KeyResultResponseDTO update(
      @PathVariable("id") String id,
      @RequestBody @Valid UpdateKeyResultRequest updateKeyResultRequest) {
    log.info("(update)id: {}, updateKeyResultRequest: {}", id, updateKeyResultRequest);
    return service.update(id, updateKeyResultRequest);
  }
}
