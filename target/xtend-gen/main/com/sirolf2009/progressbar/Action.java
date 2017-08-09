package com.sirolf2009.progressbar;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@Accessors
@SuppressWarnings("all")
public abstract class Action<T extends Object> implements Callable<T> {
  private final ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(100, true);
  
  private final ArrayBlockingQueue<Integer> progressQueue = new ArrayBlockingQueue<Integer>(100, true);
  
  private int progress = 0;
  
  private int workload = this.getWorkloadSize();
  
  private int progressBatch = 1;
  
  private int progressCounter = 0;
  
  public void setMessage(final String message) {
    this.messageQueue.offer(message);
  }
  
  public void progress() {
    this.progress(1);
  }
  
  public void progress(final int progress) {
    int _progress = this.progress;
    this.progress = (_progress + progress);
    this.progressCounter++;
    if (((this.progressCounter % this.progressBatch) == 0)) {
      this.progressQueue.add(Integer.valueOf(this.progress));
      this.progressCounter = 0;
    }
  }
  
  public abstract int getWorkloadSize();
  
  @Pure
  public ArrayBlockingQueue<String> getMessageQueue() {
    return this.messageQueue;
  }
  
  @Pure
  public ArrayBlockingQueue<Integer> getProgressQueue() {
    return this.progressQueue;
  }
  
  @Pure
  public int getProgress() {
    return this.progress;
  }
  
  public void setProgress(final int progress) {
    this.progress = progress;
  }
  
  @Pure
  public int getWorkload() {
    return this.workload;
  }
  
  public void setWorkload(final int workload) {
    this.workload = workload;
  }
  
  @Pure
  public int getProgressBatch() {
    return this.progressBatch;
  }
  
  public void setProgressBatch(final int progressBatch) {
    this.progressBatch = progressBatch;
  }
  
  @Pure
  public int getProgressCounter() {
    return this.progressCounter;
  }
  
  public void setProgressCounter(final int progressCounter) {
    this.progressCounter = progressCounter;
  }
}
