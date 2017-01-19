package org.jembi.bsis.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.jembi.bsis.model.BaseEntity;

@Entity
@Audited
public class GeneralConfig extends BaseEntity {

  private static final long serialVersionUID = 1L;

  @Column(unique = true)
  private String name;

  private String value;
  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @NotAudited
  private DataType dataType;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DataType getDataType() {
    return dataType;
  }

  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }

  public void copy(GeneralConfig generalConfig) {
    this.name = generalConfig.getName();
    this.description = generalConfig.getDescription();
    this.value = generalConfig.getValue();
    this.dataType = generalConfig.getDataType();
  }
}