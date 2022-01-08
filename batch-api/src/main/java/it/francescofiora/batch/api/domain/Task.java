package it.francescofiora.batch.api.domain;

import it.francescofiora.batch.api.domain.converter.JsonToSetConverter;
import it.francescofiora.batch.common.domain.AbstractDomain;
import it.francescofiora.batch.common.enumeration.TaskStatus;
import it.francescofiora.batch.common.enumeration.TaskType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Task Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@ToString(callSuper = true, includeFieldNames = true)
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

  @Lob
  @Column(name = "parameters")
  @Convert(converter = JsonToSetConverter.class)
  private Set<Parameter> parameters = new HashSet<>();

  @ManyToMany
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @JoinTable(name = "task_task",
      joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "dependency_id", referencedColumnName = "id"))
  private Set<Task> dependencies;

  @Column(name = "result")
  private String result;
}
