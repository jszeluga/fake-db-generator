package com.github.jszeluga.entity.dimension;

import javax.persistence.*;

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

  public void setDispositionKey(long dispositionKey) {
    this.dispositionKey = dispositionKey;
  }

  public String getOutcome() {
    return outcome;
  }

  public void setOutcome(String outcome) {
    this.outcome = outcome;
  }

  public boolean isFailureDueToCell() {
    return failureDueToCell;
  }

  public void setFailureDueToCell(boolean failureDueToCell) {
    this.failureDueToCell = failureDueToCell;
  }

  public boolean isFailureDueToDevice() {
    return failureDueToDevice;
  }

  public void setFailureDueToDevice(boolean failureDueToDevice) {
    this.failureDueToDevice = failureDueToDevice;
  }
}
