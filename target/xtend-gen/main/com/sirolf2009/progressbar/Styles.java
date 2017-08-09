package com.sirolf2009.progressbar;

import com.sirolf2009.progressbar.Progress;
import com.sirolf2009.progressbar.Style;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class Styles {
  @Data
  private static class AnimationFrame implements Delayed {
    private final String keyFrame;
    
    private final long startTime;
    
    public AnimationFrame(final String keyFrame, final long delay) {
      this.keyFrame = keyFrame;
      long _currentTimeMillis = System.currentTimeMillis();
      long _plus = (_currentTimeMillis + delay);
      this.startTime = _plus;
    }
    
    @Override
    public long getDelay(final TimeUnit unit) {
      long _currentTimeMillis = System.currentTimeMillis();
      long _minus = (this.startTime - _currentTimeMillis);
      return unit.convert(_minus, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public int compareTo(final Delayed o) {
      return Long.valueOf(this.startTime).compareTo(Long.valueOf(((Styles.AnimationFrame) o).startTime));
    }
    
    @Override
    @Pure
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((this.keyFrame== null) ? 0 : this.keyFrame.hashCode());
      result = prime * result + (int) (this.startTime ^ (this.startTime >>> 32));
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
      Styles.AnimationFrame other = (Styles.AnimationFrame) obj;
      if (this.keyFrame == null) {
        if (other.keyFrame != null)
          return false;
      } else if (!this.keyFrame.equals(other.keyFrame))
        return false;
      if (other.startTime != this.startTime)
        return false;
      return true;
    }
    
    @Override
    @Pure
    public String toString() {
      ToStringBuilder b = new ToStringBuilder(this);
      b.add("keyFrame", this.keyFrame);
      b.add("startTime", this.startTime);
      return b.toString();
    }
    
    @Pure
    public String getKeyFrame() {
      return this.keyFrame;
    }
    
    @Pure
    public long getStartTime() {
      return this.startTime;
    }
  }
  
  public final static Style SIMPLE = new Function0<Style>() {
    public Style apply() {
      abstract class __Styles_1 implements Style {
        DecimalFormat percentageFormat;
      }
      
      __Styles_1 ___Styles_1 = new __Styles_1() {
        {
          percentageFormat = new DecimalFormat("#0");
        }
        @Override
        public void setup(final int terminalWidth, @Extension final Progress progressObject) {
          InputOutput.<String>println(progressObject.getActionName());
        }
        
        @Override
        public synchronized void draw(final int terminalWidth, @Extension final Progress progressObject) {
          int _progress = progressObject.getProgress();
          int _workload = progressObject.getWorkload();
          double _divide = (((double) _progress) / ((double) _workload));
          double _multiply = (_divide * 100d);
          String _format = this.percentageFormat.format(_multiply);
          final String percentage = (_format + "%");
          Styles.clearLine(terminalWidth);
          String _orElse = progressObject.getMessage().orElse("");
          String _plus = (_orElse + " ");
          String _plus_1 = (_plus + percentage);
          InputOutput.<String>print(_plus_1);
        }
        
        @Override
        public void completed(final int terminalWidth, final Progress progress) {
          InputOutput.println();
        }
      };
      return ___Styles_1;
    }
  }.apply();
  
  public final static Style BAR = new Function0<Style>() {
    public Style apply() {
      abstract class __Styles_2 implements Style {
        DecimalFormat percentageFormat;
        
        DecimalFormat timeFormat;
        
        public abstract String orNothing(final String string);
      }
      
      __Styles_2 ___Styles_2 = new __Styles_2() {
        {
          percentageFormat = new DecimalFormat("#0");
          
          timeFormat = new DecimalFormat("00");
        }
        @Override
        public void setup(final int terminalWidth, @Extension final Progress progressObject) {
          InputOutput.<String>println(progressObject.getActionName());
        }
        
        @Override
        public synchronized void draw(final int terminalWidth, @Extension final Progress progressObject) {
          int _progress = progressObject.getProgress();
          int _workload = progressObject.getWorkload();
          double _divide = (((double) _progress) / ((double) _workload));
          double _multiply = (_divide * 100d);
          String _format = this.percentageFormat.format(_multiply);
          final String percentage = (_format + "%");
          Styles.clearLine(terminalWidth);
          final String message = progressObject.getMessage().orElse("");
          long _currentTimeMillis = System.currentTimeMillis();
          long _time = progressObject.getStarted().getTime();
          long _minus = (_currentTimeMillis - _time);
          final Duration duration = Duration.ofMillis(_minus);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("(");
          String _format_1 = this.timeFormat.format(duration.toHours());
          _builder.append(_format_1);
          _builder.append(":");
          String _format_2 = this.timeFormat.format(duration.toMinutes());
          _builder.append(_format_2);
          _builder.append(":");
          long _millis = duration.toMillis();
          long _divide_1 = (_millis / 1000);
          String _format_3 = this.timeFormat.format(_divide_1);
          _builder.append(_format_3);
          _builder.append(")");
          final String time = _builder.toString();
          final String prefix = (((message + " ") + percentage) + " [");
          final String suffix = ("] " + time);
          int _length = prefix.length();
          int _minus_1 = (terminalWidth - _length);
          int _length_1 = suffix.length();
          final int barLength = (_minus_1 - _length_1);
          int _progress_1 = progressObject.getProgress();
          int _workload_1 = progressObject.getWorkload();
          double _divide_2 = (((double) _progress_1) / ((double) _workload_1));
          double _multiply_1 = (_divide_2 * ((double) barLength));
          double _floor = Math.floor(_multiply_1);
          final int actualBarLength = ((int) _floor);
          final Function1<Integer, String> _function = new Function1<Integer, String>() {
            @Override
            public String apply(final Integer it) {
              return "=";
            }
          };
          final Function2<String, String, String> _function_1 = new Function2<String, String, String>() {
            @Override
            public String apply(final String a, final String b) {
              return (a + b);
            }
          };
          final String filledInSpace = this.orNothing(IterableExtensions.<String>reduce(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, (actualBarLength - 1), true), _function), _function_1));
          int _length_2 = filledInSpace.length();
          final int actualBar = (_length_2 + 1);
          final Function1<Integer, String> _function_2 = new Function1<Integer, String>() {
            @Override
            public String apply(final Integer it) {
              return " ";
            }
          };
          final Function2<String, String, String> _function_3 = new Function2<String, String, String>() {
            @Override
            public String apply(final String a, final String b) {
              return (a + b);
            }
          };
          final String emptySpace = this.orNothing(IterableExtensions.<String>reduce(IterableExtensions.<Integer, String>map(new ExclusiveRange(actualBar, barLength, true), _function_2), _function_3));
          final String bar = ((filledInSpace + ">") + emptySpace);
          InputOutput.<String>print(((prefix + bar) + suffix));
        }
        
        public String orNothing(final String string) {
          if ((string == null)) {
            return "";
          }
          return string;
        }
        
        @Override
        public void completed(final int terminalWidth, final Progress progress) {
          InputOutput.println();
        }
      };
      return ___Styles_2;
    }
  }.apply();
  
  public final static Style ASCII = new Function0<Style>() {
    public Style apply() {
      abstract class __Styles_3 implements Style {
        DecimalFormat percentageFormat;
        
        DecimalFormat timeFormat;
        
        public abstract String orNothing(final String string);
      }
      
      __Styles_3 ___Styles_3 = new __Styles_3() {
        {
          percentageFormat = new DecimalFormat("#0");
          
          timeFormat = new DecimalFormat("00");
        }
        @Override
        public void setup(final int terminalWidth, @Extension final Progress progressObject) {
          InputOutput.<String>println(progressObject.getActionName());
        }
        
        @Override
        public synchronized void draw(final int terminalWidth, @Extension final Progress progressObject) {
          int _progress = progressObject.getProgress();
          int _workload = progressObject.getWorkload();
          double _divide = (((double) _progress) / ((double) _workload));
          double _multiply = (_divide * 100d);
          String _format = this.percentageFormat.format(_multiply);
          final String percentage = (_format + "%");
          Styles.clearLine(terminalWidth);
          final String message = progressObject.getMessage().orElse("");
          long _currentTimeMillis = System.currentTimeMillis();
          long _time = progressObject.getStarted().getTime();
          long _minus = (_currentTimeMillis - _time);
          final Duration duration = Duration.ofMillis(_minus);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("(");
          String _format_1 = this.timeFormat.format(duration.toHours());
          _builder.append(_format_1);
          _builder.append(":");
          String _format_2 = this.timeFormat.format(duration.toMinutes());
          _builder.append(_format_2);
          _builder.append(":");
          long _millis = duration.toMillis();
          long _divide_1 = (_millis / 1000);
          String _format_3 = this.timeFormat.format(_divide_1);
          _builder.append(_format_3);
          _builder.append(")");
          final String time = _builder.toString();
          final String prefix = (((message + " ") + percentage) + " ┃");
          final String suffix = ("┃ " + time);
          int _length = prefix.length();
          int _minus_1 = (terminalWidth - _length);
          int _length_1 = suffix.length();
          final int barLength = (_minus_1 - _length_1);
          int _progress_1 = progressObject.getProgress();
          int _workload_1 = progressObject.getWorkload();
          double _divide_2 = (((double) _progress_1) / ((double) _workload_1));
          final double actualBarLength = (_divide_2 * ((double) barLength));
          double _floor = Math.floor(actualBarLength);
          final int fullBarsLength = ((int) _floor);
          final Function1<Integer, String> _function = new Function1<Integer, String>() {
            @Override
            public String apply(final Integer it) {
              return "◼";
            }
          };
          final Function2<String, String, String> _function_1 = new Function2<String, String, String>() {
            @Override
            public String apply(final String a, final String b) {
              return (a + b);
            }
          };
          final String filledInSpace = this.orNothing(IterableExtensions.<String>reduce(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, fullBarsLength, true), _function), _function_1));
          String _xifexpression = null;
          if (((actualBarLength - fullBarsLength) >= 0.5)) {
            _xifexpression = "◼";
          } else {
            _xifexpression = "▶";
          }
          final String halfBar = _xifexpression;
          int _length_2 = filledInSpace.length();
          int _length_3 = halfBar.length();
          final int actualBar = (_length_2 + _length_3);
          final Function1<Integer, String> _function_2 = new Function1<Integer, String>() {
            @Override
            public String apply(final Integer it) {
              return " ";
            }
          };
          final Function2<String, String, String> _function_3 = new Function2<String, String, String>() {
            @Override
            public String apply(final String a, final String b) {
              return (a + b);
            }
          };
          final String emptySpace = this.orNothing(IterableExtensions.<String>reduce(IterableExtensions.<Integer, String>map(new ExclusiveRange(actualBar, barLength, true), _function_2), _function_3));
          final String bar = ((filledInSpace + halfBar) + emptySpace);
          InputOutput.<String>print(((prefix + bar) + suffix));
        }
        
        public String orNothing(final String string) {
          if ((string == null)) {
            return "";
          }
          return string;
        }
        
        @Override
        public void completed(final int terminalWidth, final Progress progress) {
          InputOutput.println();
        }
      };
      return ___Styles_3;
    }
  }.apply();
  
  public final static Style INDETERMINATE_SIMPLE = new Function0<Style>() {
    public Style apply() {
      abstract class __Styles_4 implements Style {
        DelayQueue<Styles.AnimationFrame> animation;
        
        String currentKey;
      }
      
      __Styles_4 ___Styles_4 = new __Styles_4() {
        {
          animation = ObjectExtensions.<DelayQueue<Styles.AnimationFrame>>operator_doubleArrow(new DelayQueue<Styles.AnimationFrame>(), new Procedure1<DelayQueue<Styles.AnimationFrame>>() {
            @Override
            public void apply(final DelayQueue<Styles.AnimationFrame> it) {
              Styles.AnimationFrame _animationFrame = new Styles.AnimationFrame("│", 0);
              it.add(_animationFrame);
              Styles.AnimationFrame _animationFrame_1 = new Styles.AnimationFrame("╱", 100);
              it.add(_animationFrame_1);
              Styles.AnimationFrame _animationFrame_2 = new Styles.AnimationFrame("─", 200);
              it.add(_animationFrame_2);
              Styles.AnimationFrame _animationFrame_3 = new Styles.AnimationFrame("╲", 300);
              it.add(_animationFrame_3);
            }
          });
          
          currentKey = "│";
        }
        @Override
        public void setup(final int terminalWidth, @Extension final Progress progressObject) {
        }
        
        @Override
        public synchronized void draw(final int terminalWidth, @Extension final Progress progressObject) {
          Styles.clearLine(terminalWidth);
          final Styles.AnimationFrame newKey = this.animation.poll();
          String _xifexpression = null;
          if ((newKey != null)) {
            String _xblockexpression = null;
            {
              Styles.AnimationFrame _animationFrame = new Styles.AnimationFrame(this.currentKey, 100);
              this.animation.add(_animationFrame);
              this.currentKey = newKey.keyFrame;
              _xblockexpression = this.currentKey;
            }
            _xifexpression = _xblockexpression;
          } else {
            _xifexpression = this.currentKey;
          }
          final String frame = _xifexpression;
          String _orElse = progressObject.getMessage().orElse("");
          String _plus = (_orElse + " ");
          String _plus_1 = (_plus + frame);
          InputOutput.<String>print(_plus_1);
        }
        
        @Override
        public void completed(final int terminalWidth, @Extension final Progress progress) {
          Styles.clearLine(terminalWidth);
          InputOutput.<String>println(progress.getMessage().orElse(""));
        }
      };
      return ___Styles_4;
    }
  }.apply();
  
  public static void clearLine(final int terminalWidth) {
    final Function1<Integer, String> _function = new Function1<Integer, String>() {
      @Override
      public String apply(final Integer it) {
        return " ";
      }
    };
    final Function2<String, String, String> _function_1 = new Function2<String, String, String>() {
      @Override
      public String apply(final String a, final String b) {
        return (a + b);
      }
    };
    String _reduce = IterableExtensions.<String>reduce(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, terminalWidth, true), _function), _function_1);
    String _plus = ("\r" + _reduce);
    String _plus_1 = (_plus + "\r");
    InputOutput.<String>print(_plus_1);
  }
}
