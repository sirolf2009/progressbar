package com.sirolf2009.progressbar;

import com.sirolf2009.progressbar.Action;
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
            Thread.sleep(100);
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
  
  @Test
  public void test() {
    ProgressBar.Builder<String> _name = new ProgressBar.Builder<String>().name("Unit Test");
    TestProgressBar.DumbAction _dumbAction = new TestProgressBar.DumbAction();
    final ProgressBar<String> bar3 = _name.action(_dumbAction).style(Styles.ASCII).build();
    InputOutput.<String>println(bar3.get());
    ProgressBar.Builder<String> _name_1 = new ProgressBar.Builder<String>().name("Unit Test");
    TestProgressBar.DumbAction _dumbAction_1 = new TestProgressBar.DumbAction();
    final ProgressBar<String> bar2 = _name_1.action(_dumbAction_1).style(Styles.BAR).build();
    InputOutput.<String>println(bar2.get());
    ProgressBar.Builder<String> _name_2 = new ProgressBar.Builder<String>().name("Unit Test");
    TestProgressBar.DumbAction _dumbAction_2 = new TestProgressBar.DumbAction();
    final ProgressBar<String> bar = _name_2.action(_dumbAction_2).build();
    InputOutput.<String>println(bar.get());
  }
}