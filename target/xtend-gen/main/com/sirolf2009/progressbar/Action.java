package com.sirolf2009.progressbar;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

@Accessors
@SuppressWarnings("all")
public abstract class Action<T extends Object> implements Callable<T> {
  private final LinkedBlockingQueue<Pair<String, Integer>> updateQueue = new LinkedBlockingQueue<Pair<String, Integer>>();
  
  private final AtomicReference<String> message = new AtomicReference<String>();
  
  private final AtomicInteger progress = new AtomicInteger(0);
  
  private int workload = this.getWorkloadSize();
  
  public void setMessage(final String message) {
    this.message.set(message);
    this.update();
  }
  
  public void progress() {
    this.progress(1);
  }
  
  public void progress(final int progress) {
    this.progress.addAndGet(progress);
    this.update();
  }
  
  public boolean update() {
    String _get = this.message.get();
    int _get_1 = this.progress.get();
    Pair<String, Integer> _mappedTo = Pair.<String, Integer>of(_get, Integer.valueOf(_get_1));
    return this.updateQueue.add(_mappedTo);
  }
  
  public abstract int getWorkloadSize();
  
  public void onSubmitted(final Future<T> future) {
  }
  
  @Pure
  public LinkedBlockingQueue<Pair<String, Integer>> getUpdateQueue() {
    return this.updateQueue;
  }
  
  @Pure
  public AtomicReference<String> getMessage() {
    return this.message;
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
}
