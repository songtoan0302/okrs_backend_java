package org.aibles.okrs.core_business.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.aibles.okrs.core_business.validation.ModelValidator;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@MappedSuperclass
public class AbstractModel<T> extends ModelValidator<T> {

  @CreationTimestamp
  @Column(name = "created_at")
  protected Instant createdAt;

  @Column(name = "deadline_at")
  protected Instant deadlineAt;
}
