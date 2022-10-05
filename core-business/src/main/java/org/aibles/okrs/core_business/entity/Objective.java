package org.aibles.okrs.core_business.entity;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.aibles.okrs.core_business.contants.Status;

@Entity
@Data
@Table(name = "objective")
public class Objective extends AbstractModel<Objective> {

  @Id private String id;

  @NotBlank private String type;

  @NotBlank private String content;

  @NotBlank private String reason;

  private Status status;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "objective")
  private Set<KeyResult> keyResult;

  @PrePersist
  private void prePersistId() {
    this.id = this.id == null ? UUID.randomUUID().toString() : id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Objective objective = (Objective) o;
    return Objects.equals(id, objective.id);
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
