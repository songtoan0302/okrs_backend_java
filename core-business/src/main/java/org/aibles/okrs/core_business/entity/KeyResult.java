package org.aibles.okrs.core_business.entity;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;
import org.aibles.okrs.core_business.contants.Status;
import org.aibles.okrs.core_business.contants.Unit;

@Entity
@Data
@Table(name = "key_result")
public class KeyResult extends AbstractModel<KeyResult> {
  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "objective_id")
  private String objectiveId;

  @Column(name = "content")
  private String content;

  @Column(name = "target")
  private Long target;

  @Column(name = "current_achievement")
  private Long currentAchievement;

  @Column(name = "unit")
  private Unit unit;

  @Column(name = "status")
  private Status status;

  @ManyToOne
  @JoinColumn(name = "objective_id", updatable = false, insertable = false)
  private Objective objective;

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
    KeyResult keyResult = (KeyResult) o;
    return Objects.equals(id, keyResult.id);
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
