package com.sirolf2009.progressbar;

import java.util.Date;
import java.util.Optional;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@Data
@SuppressWarnings("all")
public class Progress {
  private final Date started;
  
  private final String actionName;
  
  private final Optional<String> message;
  
  private final int progress;
  
  private final int workload;
  
  public Progress(final Date started, final String actionName, final Optional<String> message, final int progress, final int workload) {
    super();
    this.started = started;
    this.actionName = actionName;
    this.message = message;
    this.progress = progress;
    this.workload = workload;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.started== null) ? 0 : this.started.hashCode());
    result = prime * result + ((this.actionName== null) ? 0 : this.actionName.hashCode());
    result = prime * result + ((this.message== null) ? 0 : this.message.hashCode());
    result = prime * result + this.progress;
    result = prime * result + this.workload;
    return result;
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Progress other = (Progress) obj;
    if (this.started == null) {
      if (other.started != null)
        return false;
    } else if (!this.started.equals(other.started))
      return false;
    if (this.actionName == null) {
      if (other.actionName != null)
        return false;
    } else if (!this.actionName.equals(other.actionName))
      return false;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
      return false;
    if (other.progress != this.progress)
      return false;
    if (other.workload != this.workload)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("started", this.started);
    b.add("actionName", this.actionName);
    b.add("message", this.message);
    b.add("progress", this.progress);
    b.add("workload", this.workload);
    return b.toString();
  }
  
  @Pure
  public Date getStarted() {
    return this.started;
  }
  
  @Pure
  public String getActionName() {
    return this.actionName;
  }
  
  @Pure
  public Optional<String> getMessage() {
    return this.message;
  }
  
  @Pure
  public int getProgress() {
    return this.progress;
  }
  
  @Pure
  public int getWorkload() {
    return this.workload;
  }
}
