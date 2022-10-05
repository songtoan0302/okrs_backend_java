package org.aibles.okrs.core_business.dto.request;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.Predicate;
import lombok.Data;
import org.aibles.okrs.core_business.contants.Status;
import org.aibles.okrs.core_business.entity.KeyResult;
import org.aibles.okrs.core_business.util.paging.PagingReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListKeyResultCriteria extends PagingReq {
  private String content;
  private Long target;
  private Long currentAchievement;
  private Status status;
  private Instant startTime;
  private Instant endTime;

  public Specification<KeyResult> toPredicate() {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (StringUtils.isNotBlank(content)) {
        predicates.add(criteriaBuilder.like(root.get("type"), StringUtils.wrap(content, "%")));
      }
      if (Objects.nonNull(target)) {
        predicates.add(criteriaBuilder.equal(root.get("target"), target));
      }
      if (Objects.nonNull(currentAchievement)) {
        predicates.add(criteriaBuilder.equal(root.get("currentAchievement"), currentAchievement));
      }
      if (Objects.nonNull(status)) {
        predicates.add(criteriaBuilder.equal(root.get("status"), status));
      }
      if (Objects.nonNull(startTime)) {
        predicates.add(criteriaBuilder.greaterThan(root.get("createdAt"), startTime));
      }
      if (Objects.nonNull(endTime)) {
        predicates.add(criteriaBuilder.lessThan(root.get("createdAt"), endTime));
      }
      return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
