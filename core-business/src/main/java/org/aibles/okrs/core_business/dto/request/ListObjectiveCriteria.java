package org.aibles.okrs.core_business.dto.request;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import lombok.Data;
import org.aibles.okrs.core_business.contants.Status;
import org.aibles.okrs.core_business.entity.Objective;
import org.aibles.okrs.core_business.util.paging.PagingReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ListObjectiveCriteria extends PagingReq {

  private String type;

  private String content;

  private String reason;

  private Status status;

  private Instant startTime;

  private Instant endTime;

  public Specification<Objective> toPredicate() {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (StringUtils.isNotBlank(type)) {
        predicates.add(criteriaBuilder.like(root.get("type"), StringUtils.wrap(type, "%")));
      }
      if (StringUtils.isNotBlank(content)) {
        predicates.add(criteriaBuilder.like(root.get("content"), StringUtils.wrap(content, "%")));
      }
      if (StringUtils.isNotBlank(reason)) {
        predicates.add(criteriaBuilder.like(root.get("reason"), StringUtils.wrap(reason, "%")));
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
      if(!query.getResultType().equals(long.class) && !query.getResultType().equals(Long.class)) {
        root.fetch("keyResult", JoinType.LEFT);
      }
      return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
