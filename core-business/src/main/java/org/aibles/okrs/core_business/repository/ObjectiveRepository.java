package org.aibles.okrs.core_business.repository;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.aibles.okrs.core_business.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectiveRepository
    extends JpaRepository<Objective, String>, JpaSpecificationExecutor<Objective> {
  boolean existsByContent(String content);

  @Lock(LockModeType.PESSIMISTIC_READ)
  Optional<Objective> findById(String id);
}
