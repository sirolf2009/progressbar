package com.sirolf2009.progressbar;

import com.sirolf2009.progressbar.Progress;
import com.sirolf2009.progressbar.Style;
import java.text.DecimalFormat;
import java.time.Duration;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class Styles {
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
