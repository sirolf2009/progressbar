package com.sirolf2009.progressbar;

import com.sirolf2009.progressbar.Action;
import com.sirolf2009.progressbar.Progress;
import com.sirolf2009.progressbar.Style;
import com.sirolf2009.progressbar.Styles;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@Data
@SuppressWarnings("all")
public class ProgressBar<T extends Object> {
  public static class Builder<T extends Object> {
    private String name;
    
    private Action<T> action;
    
    private Style style = Styles.SIMPLE;
    
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    
    private int terminalWidth = 100;
    
    public ProgressBar<T> build() {
      if ((this.name == null)) {
        throw new IllegalStateException("name must be given!");
      }
      if ((this.action == null)) {
        throw new IllegalStateException("action must be given!");
      }
      return new ProgressBar<T>(this.name, this.action, this.style, this.executor, this.terminalWidth);
    }
    
    public ProgressBar.Builder<T> name(final String name) {
      this.name = name;
      return this;
    }
    
    public ProgressBar.Builder<T> action(final Action<T> action) {
      this.action = action;
      return this;
    }
    
    public ProgressBar.Builder<T> style(final Style style) {
      this.style = style;
      return this;
    }
    
    public ProgressBar.Builder<T> executor(final ExecutorService executor) {
      this.executor = executor;
      return this;
    }
    
    public ProgressBar.Builder<T> terminalWidth(final int terminalWidth) {
      this.terminalWidth = terminalWidth;
      return this;
    }
  }
  
  private final String name;
  
  private final Action<T> action;
  
  private final Style style;
  
  private final ExecutorService executor;
  
  private final int terminalWidth;
  
  public T get() {
    try {
      final Date started = new Date();
      final AtomicReference<String> message = new AtomicReference<String>();
      final AtomicReference<Integer> progress = new AtomicReference<Integer>(Integer.valueOf(0));
      final Supplier<Progress> _function = new Supplier<Progress>() {
        @Override
        public Progress get() {
          Optional<String> _ofNullable = Optional.<String>ofNullable(message.get());
          Integer _get = progress.get();
          int _workloadSize = ProgressBar.this.action.getWorkloadSize();
          return new Progress(started, ProgressBar.this.name, _ofNullable, (_get).intValue(), _workloadSize);
        }
      };
      final Supplier<Progress> createProgress = _function;
      this.style.setup(this.terminalWidth, createProgress.get());
      final Future<T> future = this.executor.<T>submit(this.action);
      final Runnable _function_1 = new Runnable() {
        @Override
        public void run() {
          try {
            while ((!future.isDone())) {
              {
                message.set(ProgressBar.this.action.getMessageQueue().take());
                ProgressBar.this.style.draw(ProgressBar.this.terminalWidth, createProgress.get());
              }
            }
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      };
      new Thread(_function_1).start();
      final Runnable _function_2 = new Runnable() {
        @Override
        public void run() {
          try {
            while ((!future.isDone())) {
              {
                progress.set(ProgressBar.this.action.getProgressQueue().take());
                ProgressBar.this.style.draw(ProgressBar.this.terminalWidth, createProgress.get());
              }
            }
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      };
      new Thread(_function_2).start();
      final T result = future.get();
      this.style.completed(this.terminalWidth, createProgress.get());
      return result;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public ProgressBar(final String name, final Action<T> action, final Style style, final ExecutorService executor, final int terminalWidth) {
    super();
    this.name = name;
    this.action = action;
    this.style = style;
    this.executor = executor;
    this.terminalWidth = terminalWidth;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.action== null) ? 0 : this.action.hashCode());
    result = prime * result + ((this.style== null) ? 0 : this.style.hashCode());
    result = prime * result + ((this.executor== null) ? 0 : this.executor.hashCode());
    result = prime * result + this.terminalWidth;
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
    ProgressBar<?> other = (ProgressBar<?>) obj;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.action == null) {
      if (other.action != null)
        return false;
    } else if (!this.action.equals(other.action))
      return false;
    if (this.style == null) {
      if (other.style != null)
        return false;
    } else if (!this.style.equals(other.style))
      return false;
    if (this.executor == null) {
      if (other.executor != null)
        return false;
    } else if (!this.executor.equals(other.executor))
      return false;
    if (other.terminalWidth != this.terminalWidth)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("action", this.action);
    b.add("style", this.style);
    b.add("executor", this.executor);
    b.add("terminalWidth", this.terminalWidth);
    return b.toString();
  }
  
  @Pure
  public String getName() {
    return this.name;
  }
  
  @Pure
  public Action<T> getAction() {
    return this.action;
  }
  
  @Pure
  public Style getStyle() {
    return this.style;
  }
  
  @Pure
  public ExecutorService getExecutor() {
    return this.executor;
  }
  
  @Pure
  public int getTerminalWidth() {
    return this.terminalWidth;
  }
}
