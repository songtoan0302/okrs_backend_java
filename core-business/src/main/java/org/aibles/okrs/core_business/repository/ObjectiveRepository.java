package org.aibles.okrs.core_business.repository;

import org.aibles.okrs.core_business.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectiveRepository
    extends JpaRepository<Objective, String>, JpaSpecificationExecutor<Objective> {
  boolean existsByContent(String content);
}
