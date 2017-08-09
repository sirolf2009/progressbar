package com.sirolf2009.progressbar;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@Accessors
@SuppressWarnings("all")
public abstract class Action<T extends Object> implements Callable<T> {
  private final LinkedBlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();
  
  private final LinkedBlockingQueue<Integer> progressQueue = new LinkedBlockingQueue<Integer>();
  
  private final AtomicInteger progress = new AtomicInteger(0);
  
  private int workload = this.getWorkloadSize();
  
  private int progressBatch = 1;
  
  private final AtomicInteger progressCounter = new AtomicInteger(0);
  
  public void setMessage(final String message) {
    this.messageQueue.offer(message);
  }
  
  public void progress() {
    this.progress(1);
  }
  
  public void progress(final int progress) {
    this.progress.addAndGet(progress);
    int _addAndGet = this.progressCounter.addAndGet(1);
    int _modulo = (_addAndGet % this.progressBatch);
    boolean _equals = (_modulo == 0);
    if (_equals) {
      this.progressQueue.offer(Integer.valueOf(this.progress.get()));
      this.progressCounter.set(0);
    }
  }
  
  public abstract int getWorkloadSize();
  
  @Pure
  public LinkedBlockingQueue<String> getMessageQueue() {
    return this.messageQueue;
  }
  
  @Pure
  public LinkedBlockingQueue<Integer> getProgressQueue() {
    return this.progressQueue;
  }
  
  @Pure
  public AtomicInteger getProgress() {
    return this.progress;
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
  public AtomicInteger getProgressCounter() {
    return this.progressCounter;
  }
}
