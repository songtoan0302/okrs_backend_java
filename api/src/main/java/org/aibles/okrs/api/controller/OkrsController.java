package org.aibles.okrs.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aibles.okrs.core_business.dto.request.CreateOkrsRequest;
import org.aibles.okrs.core_business.dto.response.ObjectiveResponsesDTO;
import org.aibles.okrs.core_business.service.OkrsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/api/v1/okrs")
@RequiredArgsConstructor
public class OkrsController {

  private final OkrsService service;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public ObjectiveResponsesDTO create(@RequestBody CreateOkrsRequest createOkrsRequest) {
    log.info("(create)createOkrsRequest: {}", createOkrsRequest);
    return service.create(createOkrsRequest);
  }

}
