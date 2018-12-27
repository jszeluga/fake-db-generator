package com.github.jszeluga.entity.dimension;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISPOSITION_DIM")
public class DispositionDimension {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "disposition_key")
  private long dispositionKey;

  @Column(name = "outcome")
  private String outcome;

  @Column(name = "failure_due_to_cell")
  private boolean failureDueToCell;

  @Column(name = "failure_due_to_device")
  private boolean failureDueToDevice;

  public long getDispositionKey() {
    return dispositionKey;
  }

  public void setDispositionKey(final long dispositionKey) {
    this.dispositionKey = dispositionKey;
  }

  public String getOutcome() {
    return outcome;
  }

  public void setOutcome(final String outcome) {
    this.outcome = outcome;
  }

  public boolean isFailureDueToCell() {
    return failureDueToCell;
  }

  public void setFailureDueToCell(final boolean failureDueToCell) {
    this.failureDueToCell = failureDueToCell;
  }

  public boolean isFailureDueToDevice() {
    return failureDueToDevice;
  }

  public void setFailureDueToDevice(final boolean failureDueToDevice) {
    this.failureDueToDevice = failureDueToDevice;
  }
}
