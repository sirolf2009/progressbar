package com.sirolf2009.progressbar;

import com.sirolf2009.progressbar.Progress;

@SuppressWarnings("all")
public interface Style {
  public abstract void setup(final int terminalWidth, final Progress progress);
  
  public abstract void draw(final int terminalWidth, final Progress progress);
  
  public abstract void completed(final int terminalWidth, final Progress progress);
}
