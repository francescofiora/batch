package it.francescofiora.batch.api.domain;

import it.francescofiora.batch.common.domain.AbstractDomain;
import it.francescofiora.batch.common.enumeration.TaskStatus;
import it.francescofiora.batch.common.enumeration.TaskType;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task extends AbstractDomain implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "task_type", nullable = false)
  private TaskType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 25)
  private TaskStatus status = TaskStatus.NOT_STARTED;

  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @JoinTable(name = "task_parameter",
      joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "parameter_id", referencedColumnName = "id"))
  private Set<Parameter> parameters;

  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @JoinTable(name = "task_task",
      joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "dependency_id", referencedColumnName = "id"))
  private Set<Task> dependencies;

  @Column(name = "result")
  private String result;

  @Override
  public String toString() {
    return "Task{id=" + getId() + ", description='" + getDescription() + "', type='" + getType()
        + "'" + ", status='" + getStatus() + "', result = " + getResult() + "}";
  }

}
