package org.aibles.okrs.core_business.repository;

import org.aibles.okrs.core_business.entity.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyResultRepository
    extends JpaRepository<KeyResult, String>, JpaSpecificationExecutor<KeyResult> {

  Long countByObjectiveId(String objectiveId);

  boolean existsByObjectiveId(String objectiveId);

  void deleteByObjectiveId(String objectiveId);

  boolean existsByContent(String content);
}
