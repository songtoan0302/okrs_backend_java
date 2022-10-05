package org.aibles.okrs.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okrs.core_business.dto.request.CreateObjectiveRequest;
import org.aibles.okrs.core_business.dto.request.ListObjectiveCriteria;
import org.aibles.okrs.core_business.dto.request.UpdateObjectiveRequest;
import org.aibles.okrs.core_business.dto.response.ObjectiveDetailResponseDTO;
import org.aibles.okrs.core_business.dto.response.ObjectiveResponsesDTO;
import org.aibles.okrs.core_business.service.ObjectiveService;
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
@RestController
@RequestMapping("/api/v1/objectives")
@Slf4j
@RequiredArgsConstructor
public class ObjectiveController {

  private final ObjectiveService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ObjectiveDetailResponseDTO add(
      @RequestBody @Validated CreateObjectiveRequest createObjectiveRequest) {
    log.info("(add)createObjectiveRequest: {}", createObjectiveRequest);
    return service.create(createObjectiveRequest);
  }

  @DeleteMapping(path = "{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable("id") String id) {
    log.info("(delete)id: {}", id);
    service.delete(id);
  }

  @GetMapping(path = "{id}")
  @ResponseStatus(HttpStatus.OK)
  public ObjectiveResponsesDTO get(@PathVariable("id") String id) {
    log.info("(get)id: {}", id);
    return service.get(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public PagingRes<ObjectiveResponsesDTO> filter(
      @Validated ListObjectiveCriteria listObjectiveCriteria) {
    log.info("(filter)listObjectiveCriteria: {}", listObjectiveCriteria);
    return PagingRes.of(service.filter(listObjectiveCriteria));
  }

  @PutMapping("/{id}")
  public ObjectiveDetailResponseDTO update(
      @PathVariable("id") String id,
      @RequestBody @Validated UpdateObjectiveRequest updateObjectiveRequest) {
    log.info("(update)id: {}, updateObjectiveRequest: {}", id, updateObjectiveRequest);
    return service.update(id, updateObjectiveRequest);
  }
}
