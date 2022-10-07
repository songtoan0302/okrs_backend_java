package org.aibles.okrs.core_business.repository;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.aibles.okrs.core_business.entity.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyResultRepository
    extends JpaRepository<KeyResult, String>, JpaSpecificationExecutor<KeyResult> {

  Long countByObjectiveId(String objectiveId);

  boolean existsByObjectiveId(String objectiveId);

  void deleteByObjectiveId(String objectiveId);

  boolean existsByContent(String content);

  @Lock(LockModeType.PESSIMISTIC_READ)
  Optional<KeyResult> findById(String id);
}
