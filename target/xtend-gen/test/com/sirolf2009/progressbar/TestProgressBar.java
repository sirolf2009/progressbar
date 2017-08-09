package com.sirolf2009.progressbar;

import com.sirolf2009.progressbar.Action;
import com.sirolf2009.progressbar.ActionTimed;
import com.sirolf2009.progressbar.ProgressBar;
import com.sirolf2009.progressbar.Styles;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.junit.Test;

@SuppressWarnings("all")
public class TestProgressBar {
  public static class DumbAction extends Action<String> {
    @Override
    public String call() throws Exception {
      String _xblockexpression = null;
      {
        for (int i = 0; (i < 100); i++) {
          {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("I have slept ");
            _builder.append((i + 1));
            _builder.append(" times!");
            this.setMessage(_builder.toString());
            this.progress();
            Thread.sleep(50);
          }
        }
        _xblockexpression = "Done";
      }
      return _xblockexpression;
    }
    
    @Override
    public int getWorkloadSize() {
      return 100;
    }
  }
  
  public static class FastAction extends ActionTimed<String> {
    @Override
    public String call() throws Exception {
      String _xblockexpression = null;
      {
        for (int i = 0; (i < 10000); i++) {
          {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("I have slept ");
            _builder.append((i + 1));
            _builder.append(" times!");
            this.setMessage(_builder.toString());
            this.progress();
            Thread.sleep(1);
          }
        }
        _xblockexpression = "Done";
      }
      return _xblockexpression;
    }
    
    @Override
    public int getWorkloadSize() {
      return 10000;
    }
  }
  
  @Test
  public void test() {
    InputOutput.<String>println("ASCII FAST");
    ProgressBar.Builder<String> _name = new ProgressBar.Builder<String>().name("Unit Test");
    TestProgressBar.FastAction _fastAction = new TestProgressBar.FastAction();
    final ProgressBar<String> bar4 = _name.action(_fastAction).style(Styles.ASCII).build();
    InputOutput.<String>println(bar4.get());
    InputOutput.<String>println("ASCII");
    ProgressBar.Builder<String> _name_1 = new ProgressBar.Builder<String>().name("Unit Test");
    TestProgressBar.DumbAction _dumbAction = new TestProgressBar.DumbAction();
    final ProgressBar<String> bar3 = _name_1.action(_dumbAction).style(Styles.ASCII).build();
    InputOutput.<String>println(bar3.get());
    InputOutput.println();
    InputOutput.<String>println("Bar");
    ProgressBar.Builder<String> _name_2 = new ProgressBar.Builder<String>().name("Unit Test");
    TestProgressBar.DumbAction _dumbAction_1 = new TestProgressBar.DumbAction();
    final ProgressBar<String> bar2 = _name_2.action(_dumbAction_1).style(Styles.BAR).build();
    InputOutput.<String>println(bar2.get());
    InputOutput.println();
    InputOutput.<String>println("Simple");
    ProgressBar.Builder<String> _name_3 = new ProgressBar.Builder<String>().name("Unit Test");
    TestProgressBar.DumbAction _dumbAction_2 = new TestProgressBar.DumbAction();
    final ProgressBar<String> bar = _name_3.action(_dumbAction_2).build();
    InputOutput.<String>println(bar.get());
  }
}
